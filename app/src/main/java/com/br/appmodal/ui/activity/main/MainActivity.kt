package com.br.appmodal.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import com.br.appmodal.R

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        this.findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.repoFragment -> {
                    setBack(false)
                    showActionBar()
                }
                R.id.filterFragment -> {
                    hideActionBar()
                    setBack(false)
                }
                else -> {
                    showActionBar()
                    setBack(true)
                }
            }
        }
    }

    private fun hideActionBar(){
        supportActionBar?.hide()
    }
    private fun showActionBar(){
        supportActionBar?.show()
    }
    private fun setBack(value:Boolean){
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
        supportActionBar?.setHomeButtonEnabled(value)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}
