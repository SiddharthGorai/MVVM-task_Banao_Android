package com.example.mvvmtask


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Left Nav Bar
        toolbar = binding.Toolbar
        toolbar.setTitle("mvvmTask2");
//        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.search_menu)
        drawerLayout = binding.DrawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        toolbar.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.searchBar -> {
                    val searchView = menuItem.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (query != null){
                                val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
                                val navController = navHostFragment.navController
                                navController.navigate(R.id.fragmentSearch)

                                val mFragmentTransaction = supportFragmentManager.beginTransaction()
                                val mFragment = fragmentSearch()
                                val mBundle = Bundle()
                                mBundle.putString("mText", query)
                                mFragment.arguments = mBundle
                                mFragmentTransaction.add(R.id.frameLayout, mFragment).commit()


                            }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return true
                        }

                    })
                    true
                }

                else -> {
                    false
                }
            }
        }

        }

    }

