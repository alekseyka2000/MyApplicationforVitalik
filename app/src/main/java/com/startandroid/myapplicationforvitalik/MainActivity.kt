package com.startandroid.myapplicationforvitalik

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    lateinit var currentFragment: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame, HomeFragment(),"one")
        }
        currentFragment= "one"

        fun createOtherFragment(fragment: Fragment, TAG: String ){
            supportFragmentManager.inTransaction {
                add(R.id.frame, fragment,TAG)
                detach(fragment)
            }
        }

        createOtherFragment(DashboardFragment(),"two")
        createOtherFragment(NotificationFragment(),"three")
        createOtherFragment(CallsFragment(),"four")
        createOtherFragment(ContactsFragment(),"five")

        fun showFragment(s:String){
            supportFragmentManager.inTransaction {
            detach(supportFragmentManager.findFragmentByTag(currentFragment)!!)
            attach(supportFragmentManager.findFragmentByTag(s)!!)
            }
            currentFragment=s
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val listener = navView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
               R.id.navigation_home ->{showFragment("one")
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_dashboard ->{showFragment("two")
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_notifications ->{showFragment("three")
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_calls ->{showFragment("four")
                   return@setOnNavigationItemSelectedListener true
               }
                R.id.navigation_contacts ->{showFragment("five")
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
