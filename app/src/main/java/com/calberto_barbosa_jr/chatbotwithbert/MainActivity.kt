package com.calberto_barbosa_jr.chatbotwithbert

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.chatbotwithbert.databinding.ActivityMainBinding
import org.tensorflow.lite.task.text.qa.BertQuestionAnswerer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bertQuestionAnswerer: BertQuestionAnswerer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Load the TFLite model
        val modelFile = "model.tflite"
        val assetFileDescriptor = assets.openFd(modelFile)
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val modelBuffer: ByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

        bertQuestionAnswerer = BertQuestionAnswerer.createFromFile(this, modelFile)

        val questionInput = binding.questionInput
        val referenceInput = binding.referenceInput
        val answerOutput = binding.answerOutput
        val getAnswerButton = binding.getAnswerButton

        getAnswerButton.setOnClickListener {
            val question = questionInput.text.toString()
            val referenceText = referenceInput.text.toString()
            val answers = bertQuestionAnswerer.answer(referenceText, question)
            answerOutput.text = answers.joinToString("\n") { it.text }
        }
    }

}