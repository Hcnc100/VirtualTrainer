package com.d34th.nullpointer.virtual.trainer.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import coil.load
import com.d34th.nullpointer.virtual.trainer.R
import com.d34th.nullpointer.virtual.trainer.databinding.ActivityMainBinding
import com.d34th.nullpointer.virtual.trainer.databinding.NavHeaderMainBinding
import com.d34th.nullpointer.virtual.trainer.presentation.MainViewModel
import com.d34th.nullpointer.virtual.trainer.ui.extensions.hide
import com.d34th.nullpointer.virtual.trainer.ui.extensions.show
import com.d34th.nullpointer.virtual.trainer.ui.interfaces.UploadTitleToolbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UploadTitleToolbar {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val mainViModel: MainViewModel by viewModels()
    private var nameUser: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_EntrenadorVirtual)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        listenerNavigation(navController)
        subscribe(navController)
    }

    private fun subscribe(navController: NavController) {
        NavHeaderMainBinding.bind(binding.navView.getHeaderView(0)).apply {
            mainViModel.userName.observe(this@MainActivity, {
                it?.let {
                    textView.text = it
                    nameUser = it
                    changeTitle(uploadTitleWithTime() + " $nameUser")
                }
            })
            mainViModel.userImg.observe(this@MainActivity, {
                it?.let {
                    imageView.load(it)
                }

            })

        }

    }

    private fun listenerNavigation(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentRegistry,R.id.nav_splash -> binding.appBarMain.toolbar.hide()
                R.id.nav_home -> {
                    binding.appBarMain.toolbar.show()
                    changeTitle(uploadTitleWithTime() + " $nameUser")
                }
                R.id.nav_settings->{
                    binding.appBarMain.toolbar.show()
                    changeTitle("Ajustes")
                }
                else -> binding.appBarMain.toolbar.show()
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun changeTitle(newTitle: String) {
        binding.appBarMain.toolbar.title = newTitle
    }

    private fun uploadTitleWithTime(): String {
        val cal: Calendar = Calendar.getInstance()
        val prefix = when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 5..12 -> getString(R.string.morning)
            in 12..18 -> getString(R.string.after)
            else -> getString(R.string.night)

        }

        return prefix

    }
}