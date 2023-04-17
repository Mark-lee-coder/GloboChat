package com.sriyank.globochat

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

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
        statusPref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                TODO("Not yet implemented")
                true
            }
    }
}