package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuestionsActivity : AppCompatActivity() {

    private var userName: String = "Utilisateur"
    private var currentProgress = 0
    private var score = 0
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewProgress: TextView
    private lateinit var buttonCheck: Button
    private lateinit var options: List<TextView>
    private lateinit var flagImage: ImageView

    // les drapeaux  :
    private val questions = listOf(
        Question(
            flagResId = R.drawable.morocco,
            options = listOf("Maroc", "France", "Italie", "Espagne"),
            correctAnswerIndex = 0
        ),
        Question(
            flagResId = R.drawable.argentina_flag,
            options = listOf("Maroc", "Argentine", "Italie", "Espagne"),
            correctAnswerIndex = 1
        ),
        Question(
            flagResId = R.drawable.brazil_flag,
            options = listOf("Maroc", "Argentine", "Italie", "Brazil"),
            correctAnswerIndex = 3
        ),
        Question(
            flagResId = R.drawable.finland_flag,
            options = listOf("Maroc", "Argentine", "Finlanda", "Espagne"),
            correctAnswerIndex = 2
        ),
        Question(
            flagResId = R.drawable.france_flag,
            options = listOf("Maroc", "France", "Italie", "Espagne"),
            correctAnswerIndex = 1
        ),
        Question(
            flagResId = R.drawable.germany_flag,
            options = listOf("Germany", "Argentine", "Italie", "Espagne"),
            correctAnswerIndex = 0
        ),
        Question(
            flagResId = R.drawable.haiti_flag,
            options = listOf("Maroc", "Haiti", "Italie", "Espagne"),
            correctAnswerIndex = 1
        ),
        Question(
            flagResId = R.drawable.italy_flag,
            options = listOf("Maroc", "Argentine", "Italie", "Espagne"),
            correctAnswerIndex = 2
        ),
        Question(
            flagResId = R.drawable.nigeria_flag,
            options = listOf("Maroc", "Argentine", "Italie", "Nigeria"),
            correctAnswerIndex = 3
        ),
        Question(
            flagResId = R.drawable.romania_flag,
            options = listOf("Maroc", "Argentine", "Romania", "Espagne"),
            correctAnswerIndex = 2
        ),
        Question(
            flagResId = R.drawable.spain_flag,
            options = listOf("Maroc", "Argentine", "Italie", "Espagne"),
            correctAnswerIndex = 3
        ),
    )

    data class Question(val flagResId: Int, val options: List<String>, val correctAnswerIndex: Int)


    private var selectedOptionIndex: Int? = null
    private var isAnswerChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)


        userName = intent.getStringExtra("USER_NAME") ?: "Utilisateur"

        progressBar = findViewById(R.id.progressBar)

        textViewProgress = findViewById(R.id.text_view_progress)

        val buttonCheck: Button = findViewById(R.id.button_check)

        flagImage = findViewById(R.id.image_flag)

        options = listOf(
            findViewById(R.id.text_view_option_one),
            findViewById(R.id.text_view_option_two),
            findViewById(R.id.text_view_option_three),
            findViewById(R.id.text_view_option_four)
        )

        options.forEach { option ->
            option.setBackgroundResource(R.drawable.default_option_border_bg)
        }

        options.forEach { option ->
            option.setOnClickListener {
                handleOptionClick(option)
            }
        }




        buttonCheck.setOnClickListener {
            if (!isAnswerChecked) {
                if (selectedOptionIndex != null) {
                    checkAnswer()
                    isAnswerChecked = true
                    buttonCheck.text = getString(R.string.next_question)
                } else {
                    Toast.makeText(this, "Veuillez sélectionner une réponse.", Toast.LENGTH_SHORT).show()
                }
            } else {
                goToNextQuestion()
                isAnswerChecked = false
                buttonCheck.text = getString(R.string.verify)
            }
        }
        showQuestion()
    }

    private fun showQuestion() {
        val currentQuestion = questions[currentProgress]
        flagImage.setImageResource(currentQuestion.flagResId)

        options.forEachIndexed { index, option ->
            option.text = currentQuestion.options[index]
            option.setBackgroundResource(R.drawable.default_option_border_bg)
        }
    }

    private fun handleOptionClick(selectedOption: TextView) {
        options.forEach { option ->
            option.setBackgroundResource(R.drawable.default_option_border_bg)
            option.isClickable = true
        }

        val selectedIndex = options.indexOf(selectedOption)
        selectedOption.setBackgroundResource(R.drawable.selected_option_border_bg)
        selectedOption.isClickable = false

        selectedOptionIndex = selectedIndex
    }



    private fun checkAnswer() {
        if (selectedOptionIndex != null) {
            val currentQuestion = questions[currentProgress]
            val selectedOption = options[selectedOptionIndex!!]
            val correctOption = options[currentQuestion.correctAnswerIndex]

            if (selectedOptionIndex == currentQuestion.correctAnswerIndex) {
                selectedOption.setBackgroundResource(R.drawable.correct_option_border_bg)
                score++
                Toast.makeText(this, "Bonne réponse !", Toast.LENGTH_SHORT).show()
            } else {
                selectedOption.setBackgroundResource(R.drawable.wrong_option_border_bg)
                correctOption.setBackgroundResource(R.drawable.correct_option_border_bg)
                Toast.makeText(this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun goToNextQuestion() {
        if (currentProgress < questions.size - 1) {
            currentProgress++
            progressBar.progress = currentProgress
            textViewProgress.text = "${currentProgress + 1}/${questions.size}"

            options.forEach { option ->
                option.setBackgroundResource(R.drawable.default_option_border_bg)
                option.isClickable = true
            }
            selectedOptionIndex = null

            showQuestion()
        } else {
            Toast.makeText(this, "Quiz terminé ! Score: $score/${questions.size}", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("nom",userName)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        }
    }

}
