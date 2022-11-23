package com.bangkit.bahanbaku.presentation.preference

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.presentation.profile.edit.EditProfileActivity

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            KEY_EDIT_PROFILE -> {
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }

            DARK_MODE -> {
                Toast.makeText(requireContext(), "This feature is in progress", Toast.LENGTH_SHORT).show()
            }

            LANGUAGE -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

//            HELPDESK -> {
//
//            }

            LOG_OUT -> {

            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    companion object {
        private const val KEY_EDIT_PROFILE = "key_edit_profile"
        private const val DARK_MODE = "key_dark_mode"
        private const val LANGUAGE = "key_language"
        private const val HELPDESK = "key_helpdesk"
        private const val LOG_OUT = "key_log_out"
    }
}