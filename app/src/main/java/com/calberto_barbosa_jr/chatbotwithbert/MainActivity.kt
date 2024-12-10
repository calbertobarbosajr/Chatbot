package com.calberto_barbosa_jr.chatbotwithbert

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calberto_barbosa_jr.chatbotwithbert.databinding.ActivityMainBinding
import org.tensorflow.lite.task.text.qa.BertQuestionAnswerer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bertQuestionAnswerer: BertQuestionAnswerer
    private lateinit var bertViewModel: BertViewModel

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
        // Inicializar o ViewModel
        bertViewModel = ViewModelProvider(this).get(BertViewModel::class.java)

        val questionInput = binding.questionInput
        val referenceInput = binding.referenceInput
        val answerOutput = binding.answerOutput
        val getAnswerButton = binding.getAnswerButton

        // Observa o LiveData para atualizações da resposta
        bertViewModel.answer.observe(this, Observer { answer ->
            answerOutput.text = answer
        })

        // Define o listener para o botão
        getAnswerButton.setOnClickListener {
            val question = questionInput.text.toString()
            val referenceText = referenceInput.text.toString()
            bertViewModel.getAnswer(referenceText, question)
        }

    }

}