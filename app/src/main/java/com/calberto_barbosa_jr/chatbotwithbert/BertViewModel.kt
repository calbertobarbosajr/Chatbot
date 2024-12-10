package com.calberto_barbosa_jr.chatbotwithbert

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.tensorflow.lite.task.text.qa.BertQuestionAnswerer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel


class BertViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var bertQuestionAnswerer: BertQuestionAnswerer
    private val _answer = MutableLiveData<String>()
    val answer: LiveData<String> get() = _answer

    init {
        loadModel()
    }

    private fun loadModel() {
        val modelFile = "model.tflite"
        val assetFileDescriptor = getApplication<Application>().assets.openFd(modelFile)
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val modelBuffer: ByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

        bertQuestionAnswerer = BertQuestionAnswerer.createFromFile(getApplication(), modelFile)
    }

    fun getAnswer(referenceText: String, question: String) {
        val answers = bertQuestionAnswerer.answer(referenceText, question)

        // Filtrar a resposta mais completa (a mais longa)
        val bestAnswer = answers.maxByOrNull { it.text.length }

        // Verifica se existe uma resposta válida
        bestAnswer?.let {
            _answer.postValue(it.text)
        } ?: run {
            _answer.postValue("Nenhuma resposta encontrada.")
        }
    }
}

