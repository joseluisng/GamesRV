package com.joseluisng.gamesrv

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewParent
import com.joseluisng.gamesrv.fragments.GameFragment
import com.joseluisng.gamesrv.fragments.PerfilFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Agregando la vista del tablayout y los tabs
        val tabLayout: TabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Games"))
        tabLayout.addTab(tabLayout.newTab().setText("Profile"))

        //Agregando la vista de deslizamiento de pantalla con el adapter
        val vPager: ViewPager = findViewById<View>(R.id.viewPager) as ViewPager
        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)

        //Agregando el adaptador a el Viewpager
        vPager.adapter = adapter
        //la linea permite saber cuando se cambio de fragment
        vPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        //esta linea es para saber que tab se selecciono o en que tab se encuentra y en que posición esta el tab.
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
                // Toast.makeText(context, "tab: "+ tab.text, Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //Toast.makeText(context, "tab: "+ tab.text, Toast.LENGTH_SHORT).show()
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val posicion = tab!!.position
                vPager.currentItem = posicion
            }
        })


    }

    class PagerAdapter(fm: FragmentManager?, val numTabs: Int) : FragmentPagerAdapter(fm){


        override fun getItem(position: Int): Fragment? {
            return when (position){
                0-> GameFragment()
                1-> PerfilFragment()
                else -> null
            }
        }

        override fun getCount(): Int {
            return numTabs
        }

    }
}
