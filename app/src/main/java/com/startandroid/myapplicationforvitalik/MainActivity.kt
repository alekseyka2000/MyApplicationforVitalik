package com.startandroid.myapplicationforvitalik

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame, HomeFragment())
        }
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        fun replaceFragment(fragment: Fragment ){
            supportFragmentManager.inTransaction {
                replace(R.id.frame, fragment)
            }
        }
        val listener = navView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
               R.id.navigation_home ->{replaceFragment(HomeFragment())
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_dashboard ->{replaceFragment(DashboardFragment())
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_notifications ->{replaceFragment(NotificationFragment())
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_calls ->{replaceFragment(CallsFragment())
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_contacts ->{replaceFragment(ContactsFragment())
                   return@setOnNavigationItemSelectedListener true
               }
                else -> false
            }
        }
    }
}

private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}
