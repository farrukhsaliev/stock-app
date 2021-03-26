package uz.softler.stockapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.Language
import uz.softler.stockapp.databinding.DialogLangBinding
import uz.softler.stockapp.databinding.FragmentSettingsBinding
import uz.softler.stockapp.utils.MyPreferences

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val binding = FragmentSettingsBinding.bind(view)

        val myPreferences = MyPreferences(requireContext())

        binding.language.setOnClickListener {
            val dialogView = View.inflate(context, R.layout.dialog_lang, null)
            val dialogLangBinding = DialogLangBinding.bind(dialogView)
            val builder = AlertDialog.Builder(context)
            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialogLangBinding.eng.setOnClickListener {
                myPreferences.setLang(Language.EN.toString())
                dialog.dismiss()
                activity?.recreate()
            }

            dialogLangBinding.rus.setOnClickListener {
                myPreferences.setLang(Language.RU.toString())
                dialog.dismiss()
                activity?.recreate()
            }
        }
        return view
    }
}