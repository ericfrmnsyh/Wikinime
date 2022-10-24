package com.dicoding.android.wikinime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dicoding.android.wikinime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController =
            binding.fragmentContainerMain.getFragment<NavHostFragment>().navController
        val navView = binding.navViewMain

        binding.bottomNavMain.setItemSelected(navController.graph.startDestinationId)
        binding.bottomNavMain.setOnItemSelectedListener { itemId -> navView.selectedItemId = itemId }

        NavigationUI.setupWithNavController(navView, navController)
    }
}