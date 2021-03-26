package uz.softler.stockapp.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.databinding.ActivityMainBinding
import uz.softler.stockapp.utils.MyContextWrapper
import uz.softler.stockapp.utils.MyPreferences

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val findNavController = findNavController(R.id.fragment)

        binding.bottomNavView.setupWithNavController(findNavController)
    }

    override fun attachBaseContext(newBase: Context?) {
        myPreferences = newBase?.let { MyPreferences(it) }!!

        val lang = myPreferences.getLang()
        super.attachBaseContext(lang.let { MyContextWrapper.wrap(newBase, it!!) })
    }
}