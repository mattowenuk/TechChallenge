package com.example.techchallenge.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.techchallenge.R
import com.example.techchallenge.activity.ui.allsport.AllSportFragment
import com.example.techchallenge.activity.ui.article.ArticleFragment
import com.example.techchallenge.activity.ui.guide.GuideFragment
import com.example.techchallenge.activity.ui.home.HomeFragment
import com.example.techchallenge.activity.ui.mysport.MySportFragment
import com.example.techchallenge.request.PostEvent
import com.example.techchallenge.request.RequestPostStat
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    lateinit var navView: BottomNavigationView
    private var savedState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedState = savedInstanceState
        setContentView(R.layout.activity_navigation)
        navView = findViewById(R.id.nav_view)

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar_action_bar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //setting bottom navigation
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_my_sport, R.id.navigation_guide, R.id.navigation_all_sport
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //override the default fragment creation to setup custom transactions
        initialiseHomeFragment()

        //create a listener for the bottom navigation
        navView.setOnNavigationItemSelectedListener(mOnNavItemClickListener)

        //disable the reSelecting/reloading of the tabs
        navView.setOnNavigationItemReselectedListener {}
    }

    private fun initialiseHomeFragment() {
        if (savedState == null) {
            //remove the default loaded fragment
            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().remove(it).commit()
            }

            //add the Home fragment manually
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                .commit()

            //send stat to endpoint
            Log.i("mko", "Loaded with ${fragment.javaClass.simpleName}")
            RequestPostStat(this, PostEvent.DISPLAY).post()
        }
    }

    private val mOnNavItemClickListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        //replace with the correct new fragment
        var newFragment = Fragment()
        when (menuItem.itemId) {
            R.id.navigation_home -> newFragment = HomeFragment()
            R.id.navigation_my_sport -> newFragment = MySportFragment()
            R.id.navigation_guide -> newFragment = GuideFragment()
            R.id.navigation_all_sport -> newFragment = AllSportFragment()
        }
        if (savedState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, newFragment, newFragment.javaClass.simpleName)
                .addToBackStack(newFragment.javaClass.simpleName)
                .commit()

            //send stat to endpoint
            Log.i("mko", "Replace with ${newFragment.javaClass.simpleName}")
            RequestPostStat(this, PostEvent.DISPLAY).post()
        }

        return@OnNavigationItemSelectedListener true
    }

    override fun onBackPressed() {
        super.onBackPressed()

        //get tag from newly displayed fragment and set the bottom navigation selection correctly
        val tag = supportFragmentManager.fragments[0].tag

        when (tag) {
            HomeFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_home).isChecked = true
            MySportFragment().javaClass.simpleName,
            ArticleFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_my_sport).isChecked = true
            GuideFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_guide).isChecked = true
            AllSportFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_all_sport).isChecked = true
        }

        //send stat to endpoint
        Log.i("mko", "Back to $tag")
        RequestPostStat(this, PostEvent.DISPLAY).post()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
