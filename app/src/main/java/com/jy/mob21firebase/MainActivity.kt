package com.jy.mob21firebase

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.jy.mob21firebase.core.services.AuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authService: AuthService
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavController()
        setupConfig()
        checkLoginStatus()

    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)||super.onNavigateUp()
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    private fun setupConfig() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout).apply {
            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.homeFragment, R.id.profileFragment), this
            )
        }

        val navView = findViewById<NavigationView>(R.id.navigationView).apply { this.setupWithNavController(navController) }
        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            authService.logOut()
            navController.navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true)
                    .build()
            )
            drawerLayout.close()
            true
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
            setSupportActionBar(this)
            this.setupWithNavController(navController, appBarConfiguration)
            setupActionBarWithNavController(navController,appBarConfiguration)
        }


        navController.addOnDestinationChangedListener{ _, dest, _ ->
           toolbar.visibility = when(dest.id) {
               R.id.loginFragment, R.id.registerFragment -> View.GONE
               else -> View.VISIBLE
           }
        }
    }

    private fun checkLoginStatus() {
        if (authService.getUid() != null) {
            navController.navigate(
                R.id.homeFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()
            )
        }
    }

}