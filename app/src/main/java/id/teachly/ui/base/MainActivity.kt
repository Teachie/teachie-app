package id.teachly.ui.base

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.teachly.R
import id.teachly.databinding.ActionbarMainBinding
import id.teachly.databinding.ActivityMainBinding
import id.teachly.utils.Helpers.hideView
import id.teachly.utils.Helpers.showView
import id.teachly.utils.Helpers.tag

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionbarBinding: ActionbarMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        actionbarBinding = binding.layoutActionBar
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.navHostFragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.exploreFragment, R.id.discussFragment, R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d(this.tag(), "label: ${destination.label}")
            when (destination.label) {
                "Explore", "Discussion" -> {
                    actionbarBinding.apply {
                        tvTitle.hideView()
                        contentSearch.showView()
                        searchView.apply {
                            showView()
                            queryHint = getHintTitle(destination.label.toString())
                        }
                    }
                }
                else -> {
                    actionbarBinding.apply {
                        contentSearch.hideView()
                        tvTitle.apply {
                            showView()
                            text = destination.label
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_notification -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getHintTitle(label: String): String {
        return when (label) {
            "Explore" -> {
                "Mau belajar apa hari ini?"
            }
            "Discussion" -> {
                "Cari topik diskusi favoritmu"
            }
            else -> ""
        }
    }
}