package com.example.cloneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cloneapp.fragment.HomeFragment
import com.example.cloneapp.fragment.PostFragment
import com.example.cloneapp.fragment.ProfileFragment
import com.example.cloneapp.fragment.ReelFragment
import com.example.cloneapp.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.home->{
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.search->{
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.add->{
                    replaceFragment(PostFragment())
                    true
                }
                R.id.reel->{
                    replaceFragment(ReelFragment())
                    true
                }
                R.id.profile->{
                    replaceFragment(ProfileFragment())
                    true
                }
                else->false
            }
        }
        replaceFragment(HomeFragment())
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(  R.id.fragment_container,fragment).commit()

    }
}
