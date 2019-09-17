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
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        navView = findViewById(R.id.nav_view)

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar_action_bar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

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

        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        val fragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
            .commit()

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navView.setOnNavigationItemReselectedListener {  }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        var fragment = Fragment()
        when (menuItem.itemId) {
            R.id.navigation_home -> fragment = HomeFragment()
            R.id.navigation_my_sport -> fragment = MySportFragment()
            R.id.navigation_guide -> fragment = GuideFragment()
            R.id.navigation_all_sport -> fragment = AllSportFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
        return@OnNavigationItemSelectedListener true
    }

    override fun onBackPressed() {
        super.onBackPressed()

        when(supportFragmentManager.fragments[0].tag) {
            HomeFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_home).isChecked = true
            MySportFragment().javaClass.simpleName,
                ArticleFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_my_sport).isChecked = true
            GuideFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_guide).isChecked = true
            AllSportFragment().javaClass.simpleName -> navView.menu.findItem(R.id.navigation_all_sport).isChecked = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
