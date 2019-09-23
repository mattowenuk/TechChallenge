package com.example.techchallenge.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.techchallenge.R
import com.example.techchallenge.activity.ui.article.ArticleFragment
import com.example.techchallenge.activity.ui.home.HomeFragment
import com.example.techchallenge.request.PostEvent
import com.example.techchallenge.request.RequestPostStat
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        navView = findViewById(R.id.nav_view)

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar_action_bar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //setting bottom navigation
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_my_sport, R.id.navigation_guide, R.id.navigation_all_sport
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //disable the reSelecting/reloading of the tabs
        navView.setOnNavigationItemReselectedListener {}

        //create a listener for the bottom navigation
        navView.setOnNavigationItemSelectedListener(mOnNavItemClickListener)
    }

    private val mOnNavItemClickListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

        checkAndRemoveArticleFragment()

        when (menuItem.itemId) {
            R.id.navigation_home -> navController.navigate(R.id.navigation_home)
            R.id.navigation_my_sport -> navController.navigate(R.id.navigation_my_sport)
            R.id.navigation_guide -> navController.navigate(R.id.navigation_guide)
            R.id.navigation_all_sport -> navController.navigate(R.id.navigation_all_sport)
        }

        //send stat to endpoint
        Log.i("mko", "Navigate to ${navController.currentDestination?.label}")
        RequestPostStat(this, PostEvent.DISPLAY).post()

        return@OnNavigationItemSelectedListener true
    }

    private fun checkAndRemoveArticleFragment() : Boolean {
        supportFragmentManager.fragments.forEach { parentFragment ->
            parentFragment.childFragmentManager.fragments.forEach { childFragment ->
                //Log.i("mko", "tag: " + childFragment.tag)

                if (childFragment.tag == ArticleFragment().javaClass.simpleName) {
                    Log.i("mko", "Removing article")
                    parentFragment.childFragmentManager.beginTransaction()
                        .remove(childFragment)
                        .commit()
                    return true
                }
            }
        }
        return false
    }

    override fun onBackPressed() {

        if(checkAndRemoveArticleFragment()) {
            super.onBackPressed()
            navController.navigate(R.id.navigation_my_sport)
        } else {
            super.onBackPressed()
        }

        //send stat to endpoint
        Log.i("mko", "Back to ${navController.currentDestination?.label}")
        RequestPostStat(this, PostEvent.DISPLAY).post()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
