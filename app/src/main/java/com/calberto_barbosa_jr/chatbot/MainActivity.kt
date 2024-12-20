package com.calberto_barbosa_jr.chatbot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.tensorflow.lite.task.text.qa.QuestionAnswerer
import org.tensorflow.lite.task.text.qa.BertQuestionAnswerer
import com.calberto_barbosa_jr.chatbot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var questionInput: EditText
    private lateinit var responseOutput: TextView
    private lateinit var askButton: Button
    private var qaClient: QuestionAnswerer? = null

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

        questionInput = binding.questionInput
        responseOutput = binding.responseOutput
        askButton = binding.askButton

        // Tenta carregar o modelo ALBERT
        try {
            qaClient = BertQuestionAnswerer.createFromFile(applicationContext, "1.tflite")
        } catch (e: Exception) {
            Log.e("TensorFlow", "Erro ao carregar o modelo ALBERT: ${e.message}", e)
            responseOutput.text = "Erro ao carregar o modelo ALBERT. Verifique se ele está no diretório 'assets'."
        }

        // Configura o botão para processar a pergunta
        askButton.setOnClickListener {
            val question = preprocessQuestion(questionInput.text.toString())
            val context = """
                Bem-vindo à Nossa Pizzaria!
                Estamos prontos para tornar seu dia mais saboroso!

                Horário de Funcionamento:
                Loja: das 9h às 18h.
                Entregas: das 9h às 22h.

                Serviço de Entrega:
                Levamos nossas deliciosas pizzas até você! Consulte o preço da entrega para sua região ao realizar o pedido.

                Nossas Pizzas
                1. Calabresa custa R$ 42,00
                Uma clássica e irresistível escolha que conquista fãs no mundo inteiro. Composta por calabresa e cebola (em algumas regiões, também leva queijo), representa mais de 30% dos pedidos!

                2. Portuguesa – Preço R$ 40,00
                Uma explosão de sabores com ovos, cebola, azeitona, ervilha, queijo e presunto. É a segunda pizza mais amada pelos brasileiros!

                3. Marguerita – R$ 50,00
                Um ícone italiano com um toque de simplicidade: molho, muçarela, tomate e manjericão. Perfeita para quem prefere opções sem carne.

                Bebidas:
                As bebidas são vendidas separadamente, oferecendo uma grande variedade para acompanhar sua pizza favorita. Escolha entre refrigerantes, sucos e muito mais!

                Faça seu pedido agora e aproveite os sabores que você ama. Estamos ansiosos para atender você!
            """.trimIndent()


            // Processa a pergunta e o contexto
            if (qaClient == null) {
                responseOutput.text = "Modelo não carregado. Verifique os logs para mais detalhes."
                return@setOnClickListener
            }

            // Obter a resposta do modelo
            //val answers = qaClient!!.answer(context, question)
            var textReference = binding.editTextTexReference.text.toString()
            val answers = qaClient!!.answer( textReference , question)

            // Processar a melhor resposta
            val bestAnswer = answers.firstOrNull()?.text?.let { postProcessAnswer(it) }
                ?: "Desculpe, não sei a resposta para isso."
            responseOutput.text = bestAnswer
        }
    }

    /**
     * Pré-processa a pergunta, adicionando "?" caso necessário.
     */
    private fun preprocessQuestion(question: String): String {
        return if (!question.endsWith("?")) "$question?" else question
    }

    /**
     * Pós-processa a resposta para remover partes irrelevantes.
     * - Remove palavras soltas no final da resposta.
     * - Garante que a resposta termine com um ponto final ou frase completa.
     */
    private fun postProcessAnswer(answer: String): String {
        // Remove quebras de linha desnecessárias e espaços extras
        var processed = answer.replace("\n", " ").trim()

        // Tentar capturar apenas a primeira frase completa
        val sentenceEnd = Regex("[.!?]")
        val match = sentenceEnd.find(processed)

        // Retorna apenas até o primeiro ponto final, exclamação ou interrogação
        if (match != null) {
            processed = processed.substring(0, match.range.last + 1).trim()
        }

        return processed
    }
}