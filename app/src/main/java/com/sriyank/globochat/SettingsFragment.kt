package com.sriyank.globochat

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val datastore = DataStore()
        //Enable PreferenceDataStore for entire hierarchy and disables the SharedPreferences
//        preferenceManager.preferenceDataStore = datastore

        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))
        accSettingsPref?.setOnPreferenceClickListener {
            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)

            true
        }

        //Read Preference values in a fragment

        //Step 1: Get reference to the SharedPreferences (XML file)
        val sharedPreferences = context?.let {
            PreferenceManager.getDefaultSharedPreferences(it)
        }

        //Step 2: Get the 'value' using the 'key'
        val autoReplyTime = sharedPreferences?.getString(getString(R.string.key_auto_reply_time), "")
        val autoDownload = sharedPreferences?.getBoolean(getString(R.string.key_auto_download), false)

        val statusPref = findPreference<EditTextPreference>(getString(R.string.key_status))
        statusPref?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val newStatus = newValue as String
            if (newStatus.contains("bad")) {
                Toast.makeText(context, "Inappropriate Status. Please maintain community guidelines.", Toast.LENGTH_LONG).show()
                false //false: reject the new value.
            }
            else {
                true//true: accept the new value
            }
        }

        val notificationPref = findPreference<SwitchPreferenceCompat>(getString(R.string.key_new_msg_notif))
        notificationPref?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> { switchPref ->
            if (switchPref.isChecked) {
                "Status: ON"
            }
            else {
                "Status: OFF"
            }
        }

        notificationPref?.preferenceDataStore = datastore

        val isNotifEnabled = datastore.getBoolean("key_new_msg_notif", false)
    }

    class DataStore : PreferenceDataStore() {
        override fun getBoolean(key: String?, defValue: Boolean): Boolean {
            if (key == "key_new_msg_notif") {
                //Retrieve value from cloud or local db
            }

            return defValue
        }

        override fun putBoolean(key: String?, value: Boolean) {
            if (key == "key_new_msg_notif") {
                //Save value to cloud or local db
            }
        }
    }
}