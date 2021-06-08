package com.steamtofu.mejaku.ui.classes

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
//import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.steamtofu.mejaku.R
import com.steamtofu.mejaku.database.Classes
import com.steamtofu.mejaku.databinding.ActivityCreateClassBinding
import com.steamtofu.mejaku.helper.DateHelper
import com.steamtofu.mejaku.viewmodel.ViewModelFactory
import java.io.File


//class CreateClassActivity : AppCompatActivity(), View.OnClickListener {
//
//    private lateinit var binding: ActivityCreateClassBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCreateClassBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnChooseFile.setOnClickListener(this)
//        binding.btnCreateClass.setOnClickListener(this)
//
//    }
//
//    // using uri path to do something here

//    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//    binding.tvFilePath.text = uri?.path
//    Log.d("uri", "onContentTake: ${uri?.path}")
//    }

//
//    override fun onClick(v: View?) {
//        when(v?.id){
//            R.id.btn_choose_file -> {
//                getContent.launch("*/*")
//            }
//            R.id.btn_create_class -> {
//            }
//        }
//    }
//}

class CreateClassActivity : AppCompatActivity() {


    private var _activityCreateClassBinding: ActivityCreateClassBinding? = null
    private val binding get() = _activityCreateClassBinding

    companion object {
        const val EXTRA_CLASS = "extra_class"
//        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var isEdit = false
    private var classes: Classes? = null
    private var position = 0
    private lateinit var createClassViewModel: CreateClassViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityCreateClassBinding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        createClassViewModel = obtainViewModel(this@CreateClassActivity)

        classes = intent.getParcelableExtra(EXTRA_CLASS)
        if (classes != null) {
//            position = intent.getIntExtra(EXTRA_POSITION, 0)
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
//                val intent = Intent().apply {
//                    putExtra(EXTRA_CLASS, classes)
//                    putExtra(EXTRA_POSITION, position)
//                }
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
//        binding?.btnChooseFile?.setOnClickListener{
//            val intent = Intent()
//                .setType("*/*")
//                .setAction(Intent.ACTION_GET_CONTENT)
//
//            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
//            getContent.launch("*/*")
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
////            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
////            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
//        }
//        return super.onOptionsItemSelected(item)
//    }
//    override fun onBackPressed() {
//        showAlertDialog(ALERT_DIALOG_CLOSE)
//    }
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


//    private fun showAlertDialog(type: Int) {
//       val isDialogClose = type == ALERT_DIALOG_CLOSE
//        val dialogTitle: String
//        val dialogMessage: String
//        if (isDialogClose) {
//            dialogTitle = getString(R.string.cancel)
//            dialogMessage = getString(R.string.message_cancel)
//        } else {
//            dialogMessage = getString(R.string.message_delete)
//            dialogTitle = getString(R.string.delete)
//        }
//        val alertDialogBuilder = AlertDialog.Builder(this)
//        with(alertDialogBuilder) {
//            setTitle(dialogTitle)
//            setMessage(dialogMessage)
//            setCancelable(false)
//            setPositiveButton(getString(R.string.yes)) { _, _ ->
//                if (!isDialogClose) {
//                    createClassViewModel.delete(classes as Classes)
//                    val intent = Intent()
//                    intent.putExtra(EXTRA_POSITION, position)
//                    setResult(RESULT_DELETE, intent)
//                }
//                finish()
//            }
//            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
//        }
//        val alertDialog = alertDialogBuilder.create()
//        alertDialog.show()
//    }

    private fun openFile(url: File) {
        try {
            val uri = Uri.fromFile(url)
            val intent = Intent(Intent.ACTION_VIEW)
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword")
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf")
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel")
            } else if (url.toString().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip")
            } else if (url.toString().contains(".rar")) {
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed")
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf")
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav")
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif")
            } else if (url.toString().contains(".jpg") || url.toString()
                    .contains(".jpeg") || url.toString().contains(".png")
            ) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg")
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain")
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                url.toString().contains(".mpeg") || url.toString()
                    .contains(".mpe") || url.toString().contains(".mp4") || url.toString()
                    .contains(".avi")
            ) {
                // Video files
                intent.setDataAndType(uri, "video/*")
            } else {
                intent.setDataAndType(uri, "*/*")
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No application found which can open the file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 111 && resultCode == RESULT_OK) {
//            val selectedFile = data?.data //The uri with the location of the file
//        }
//    }
//    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        binding?.tvFilePath?.text = uri?.path
//        Log.d("uri", "onContentTake: ${uri?.path}")
//    }
}