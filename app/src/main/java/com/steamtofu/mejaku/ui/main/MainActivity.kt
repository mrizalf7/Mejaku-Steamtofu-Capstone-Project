package com.steamtofu.mejaku.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.steamtofu.mejaku.R
import com.steamtofu.mejaku.database.Classes
import com.steamtofu.mejaku.ui.classes.CreateClassActivity
import com.steamtofu.mejaku.databinding.ActivityMainBinding
import com.steamtofu.mejaku.viewmodel.ViewModelFactory

//class MainActivity : AppCompatActivity(), View.OnClickListener {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.fabCreateClass.setOnClickListener(this)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.option_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onClick(v: View) {
//        when(v.id){
//            R.id.fab_create_class -> {
//                val intent = Intent(this, CreateClassActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
//}

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: ClassesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllClasses().observe(this, classObserver)

        adapter = ClassesAdapter(this@MainActivity)
        binding?.rvMainClasses?.layoutManager = LinearLayoutManager(this)
        binding?.rvMainClasses?.setHasFixedSize(true)
        binding?.rvMainClasses?.adapter = adapter

        supportActionBar?.title="Classes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.fabCreateClass?.setOnClickListener { view ->
            if (view.id == R.id.fab_create_class) {
                val intent = Intent(this@MainActivity, CreateClassActivity::class.java)
                startActivityForResult(intent,CreateClassActivity.REQUEST_ADD)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
    private val classObserver = Observer<PagedList<Classes>> { classList ->
        if (classList != null) {
//            adapter.setListClasses(classList)
            adapter.submitList(classList)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == CreateClassActivity.REQUEST_ADD) {
                if (resultCode == CreateClassActivity.RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                }
            } else if (requestCode == CreateClassActivity.REQUEST_UPDATE) {
                if (resultCode == CreateClassActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (resultCode == CreateClassActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }
    }
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding?.root as View, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}