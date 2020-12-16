package id.teachly.ui.base

import android.content.Intent
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
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.notification.NotificationActivity
import id.teachly.ui.search.SearchActivity
import id.teachly.utils.Helpers.getQuerySubmit
import id.teachly.utils.Helpers.hideView
import id.teachly.utils.Helpers.showToast
import id.teachly.utils.Helpers.showView
import id.teachly.utils.Helpers.tag

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionbarBinding: ActionbarMainBinding
    private var isContentCreator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        actionbarBinding = binding.layoutActionBar
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        FirestoreUser.getUserById(Auth.getUserId() ?: "") {
            if (it.creator != null) isContentCreator = it.creator
            if (!isUserContentCreator()) binding.navView.menu.findItem(R.id.addSectionFragment).isVisible =
                false
        }

        val navController = findNavController(R.id.navHostFragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.exploreFragment,
                0,
                R.id.discussFragment,
                R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.apply {
            setupWithNavController(navController)


            menu.findItem(R.id.addSectionFragment).setOnMenuItemClickListener {
                showToast(this@MainActivity, "Add item")
                return@setOnMenuItemClickListener true
            }
        }

        actionbarBinding.searchView.getQuerySubmit {
            startActivity(Intent(this, SearchActivity::class.java)
                .apply { putExtra(SearchActivity.SEARCH_EXTRA, it) })
        }

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
            R.id.action_notification -> startActivity(
                Intent(
                    this,
                    NotificationActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getHintTitle(label: String): String {
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

    private fun isUserContentCreator() = isContentCreator
}