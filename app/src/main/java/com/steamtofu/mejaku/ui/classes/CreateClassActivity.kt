package com.steamtofu.mejaku.ui.classes


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.steamtofu.mejaku.R
import com.steamtofu.mejaku.database.Classes
import com.steamtofu.mejaku.databinding.ActivityCreateClassBinding
import com.steamtofu.mejaku.helper.DateHelper
import com.steamtofu.mejaku.viewmodel.ViewModelFactory




class CreateClassActivity : AppCompatActivity() {


    private var _activityCreateClassBinding: ActivityCreateClassBinding? = null
    private val binding get() = _activityCreateClassBinding

    companion object {
        const val EXTRA_CLASS = "extra_class"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301

    }
    private var isEdit = false
    private var classes: Classes? = null
    private lateinit var createClassViewModel: CreateClassViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityCreateClassBinding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        createClassViewModel = obtainViewModel(this@CreateClassActivity)

        classes = intent.getParcelableExtra(EXTRA_CLASS)
        if (classes != null) {
            isEdit = true
        } else {
            classes = Classes()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (classes != null) {
                classes?.let { classes ->
                    binding?.edtClassName?.setText(classes.className)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add) 
            btnTitle = getString(R.string.create_class)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.btnCreateClass?.text = btnTitle

        binding?.btnCreateClass?.setOnClickListener {
            val className = binding?.edtClassName?.text.toString().trim()
            if (className.isEmpty()) {
                binding?.edtClassName?.error = getString(R.string.empty)
            } else {
                classes.let { classes ->
                    classes?.className = className
                }

                if (isEdit) {
                    createClassViewModel.update(classes as Classes)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    classes.let { classes ->
                        classes?.date = DateHelper.getCurrentDate()
                    }
                    createClassViewModel.insert(classes as Classes)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            val dialogTitle = getString(R.string.delete)
            val dialogMessage = getString(R.string.message_delete)
            val alertDialogBuilder = AlertDialog.Builder(this)
            with(alertDialogBuilder) {
                setTitle(dialogTitle)
                setMessage(dialogMessage)
                setCancelable(false)
                setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                setPositiveButton(getString(R.string.yes)) { _, _ ->
                   createClassViewModel.delete(classes as Classes)
                    setResult(RESULT_DELETE,intent)
                    finish()
                }

            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityCreateClassBinding = null
    }
    private fun obtainViewModel(activity: AppCompatActivity): CreateClassViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(CreateClassViewModel::class.java)
    }
}