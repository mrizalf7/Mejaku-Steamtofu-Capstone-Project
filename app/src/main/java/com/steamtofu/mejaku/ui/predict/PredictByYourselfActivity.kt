package com.steamtofu.mejaku.ui.predict

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.steamtofu.mejaku.R
import com.steamtofu.mejaku.databinding.ActivityPredictByYourselfBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


class PredictByYourselfActivity : AppCompatActivity() {


    private lateinit var _predictByYourselfBinding :ActivityPredictByYourselfBinding
    private lateinit var tfLite: Interpreter
    private lateinit var edtScore1 : EditText
    private lateinit var edtScore2 : EditText
    private lateinit var edtScore3 : EditText
    private lateinit var edtScore4: EditText
    private lateinit var predictButton : Button
    private lateinit var predictResult : TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        _predictByYourselfBinding = ActivityPredictByYourselfBinding.inflate(layoutInflater)
        setContentView(_predictByYourselfBinding.root)
        edtScore1=_predictByYourselfBinding.edtTextScore1
        edtScore2=_predictByYourselfBinding.edtTextScore2
        edtScore3=_predictByYourselfBinding.edtTextScore3
        edtScore4=_predictByYourselfBinding.edtTextScore4
        predictButton=_predictByYourselfBinding.predictButton
        predictResult=_predictByYourselfBinding.predictResult
        tfLite = loadModelFile()?.let { Interpreter(it) }!!
        supportActionBar?.title="Predict By Yourself"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

                predictButton.setOnClickListener {
                    val quiz =edtScore1.text.toString().trim()
                    val assignment1 =edtScore2.text.toString().trim()
                    val assignment2 =edtScore3.text.toString().trim()
                    val assignment3 =edtScore4.text.toString().trim()


                    when{
                        TextUtils.isEmpty(quiz) ->{
                            edtScore1.error = "This field can't be empty"
                        }
                        TextUtils.isEmpty(assignment1) -> {
                            edtScore2.error = "This field can't be empty"
                        }
                        TextUtils.isEmpty(assignment2) ->{
                            edtScore3.error = "This field can't be empty"
                        }
                        TextUtils.isEmpty(assignment3)->{
                            edtScore4.error = "This field can't be empty"
                        }
                        !TextUtils.isDigitsOnly(quiz) ->{
                            edtScore1.error = "This field can only contain numeric values"
                        }
                        !TextUtils.isDigitsOnly(assignment1) ->{
                            edtScore2.error = "This field can only contain numeric values"
                        }
                        !TextUtils.isDigitsOnly(assignment2) ->{
                            edtScore3.error = "This field can only contain numeric values"
                        }
                        !TextUtils.isDigitsOnly(assignment3) ->{
                            edtScore4.error = "This field can only contain numeric values"
                        }
                        else->{
                            val prediction = doInterference(quiz,assignment1,
                                assignment2,assignment3)
                            predictResult.text =resources.getString(R.string.predict_score,prediction.toString())
                        }
                    }


                }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer? {
        val fileDescriptor = this.assets.openFd("mejaku_model_v2.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declareLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declareLength)
    }
    private fun doInterference(
        inputString0: String,
        inputString1: String,
        inputString2: String,
        inputString3: String
    ): Float {
        val inputVal = FloatArray(4)
        inputVal[0] = java.lang.Float.valueOf(inputString0)
        inputVal[1] = java.lang.Float.valueOf(inputString1)
        inputVal[2] = java.lang.Float.valueOf(inputString2)
        inputVal[3] = java.lang.Float.valueOf(inputString3)
        val outputVal = Array(1) {
            FloatArray(
                1
            )
        }
        tfLite.run(inputVal, outputVal)
        return outputVal[0][0]
    }

}