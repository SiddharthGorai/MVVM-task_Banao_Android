package com.example.mvvmtask


import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmtask.databinding.ActivityMainBinding
import com.example.mvvmtask.viewModel.myViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private val viewModel: myViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Left Nav Bar
        toolbar = binding.Toolbar
        toolbar.setTitle("mvvmTask2");
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


        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.searchBar -> {
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.fragmentSearch)

//                    val navHostFragment =
//                        supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
//                    val navController = navHostFragment.navController
//                    navController.navigate(R.id.fragmentSearch)


                    val searchView = menuItem.actionView as SearchView
                    io.reactivex.Observable.create<String> { emitter ->
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (query != null) {
                                    val mFragmentTransaction =
                                        supportFragmentManager.beginTransaction()
                                    val mFragment = fragmentSearch()
                                    val mBundle = Bundle()
                                    mBundle.putString("mText", query)
                                    mFragment.arguments = mBundle
                                    mFragmentTransaction.add(R.id.frameLayout, mFragment).commit()
//                                    viewModel.searchPhotos(query)

                                    searchView.clearFocus()

                                }
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                if (!emitter.isDisposed) {
                                    if (newText != null) {
                                        emitter.onNext(newText)
                                    }
                                }
                                return false
                            }

                        })
                    }
                        .debounce(1000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { query ->

                                if (query != null) {
                                    Toast.makeText(this, query, Toast.LENGTH_LONG).show()
//                                    Log.d("text", query)
                                    val mFragmentTransaction =
                                        supportFragmentManager.beginTransaction()
                                    val mFragment = fragmentSearch()
                                    val mBundle = Bundle()
                                    mBundle.putString("mText", query)
                                    mFragment.arguments = mBundle
                                    mFragmentTransaction.replace(R.id.frameLayout, mFragment).commit()

//                                    viewModel.searchPhotos(query)
                                }
                            }, {

                            }, {

                            }
                        )
                    true
                }

                else -> {
                    false
                }
            }
        }


    }
}



