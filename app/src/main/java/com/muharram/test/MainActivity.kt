package com.muharram.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

lateinit var tabLayout:TabLayout
lateinit var tabViewPager:ViewPager2
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(),FirstFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        tabLayout  = findViewById(R.id.tabs)
        tabViewPager = findViewById(R.id.pager)

        tabViewPager.adapter=object : FragmentStateAdapter(this){

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> FirstFragment.newInstance()
                    1 -> SecondFragment.newInstance("","")
                    2 -> ThirdFragment.newInstance("","")
                    else -> FirstFragment.newInstance( )
                }
            }

            override fun getItemCount(): Int {
                return 3
            }
        }

        TabLayoutMediator(tabLayout, tabViewPager){tab, position ->
            tab.text = when(position){
                0 -> "ToDo"
                1 -> "In Progress"
                2 -> "Done"
                else -> null
            }
        }.attach()
    }

    override fun onTaskSelected(taskId: UUID) {
        Log.d(TAG, "MainActivity.onTaskSelected: $taskId")
    }



}