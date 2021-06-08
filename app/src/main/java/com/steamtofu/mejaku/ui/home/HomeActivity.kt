package com.steamtofu.mejaku.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.steamtofu.mejaku.R
import com.steamtofu.mejaku.databinding.ActivityCreateClassBinding
import com.steamtofu.mejaku.databinding.ActivityHomeBinding
import com.steamtofu.mejaku.ui.main.MainActivity
import com.steamtofu.mejaku.ui.predict.PredictByYourselfActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var _activityHomeBinding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(_activityHomeBinding.root)

        supportActionBar?.title = "Home"
        _activityHomeBinding.btnCreateClass.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        _activityHomeBinding.predictByYourself.setOnClickListener {
            val intent = Intent(this,PredictByYourselfActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}