package com.openclassrooms.realestatemanager.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.stetho.Stetho
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.fragment.DetailsPropertyFragment
import com.openclassrooms.realestatemanager.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        this.configureFragment()
        this.configureDetailsPropertyFragment()

    }

    private fun configureFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = MainFragment()
        fragmentTransaction.add(R.id.main_fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun configureDetailsPropertyFragment(){
        var detailsPropertyFragment = supportFragmentManager.findFragmentById(R.id.details_of_the_property_container)

        if (detailsPropertyFragment == null && details_of_the_property_container != null){
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            detailsPropertyFragment = DetailsPropertyFragment()
            fragmentTransaction.add(R.id.details_of_the_property_container, detailsPropertyFragment)
            fragmentTransaction.commit()
        }
    }


}
