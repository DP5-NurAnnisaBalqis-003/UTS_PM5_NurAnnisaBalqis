package com.example.geoboost

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoboost.databinding.ActivitySoalBinding

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    var userSelectedAnswer: Int? = null
)

class Soal : AppCompatActivity() {
    private lateinit var binding: ActivitySoalBinding
    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0

    private val sharedPreferences by lazy {
        getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questions = listOf(
            Question(getString(R.string.question1), listOf(getString(R.string.opsi_a1), getString(R.string.opsi_b1), getString(R.string.opsi_c1), getString(R.string.opsi_d1)), 0),
            Question(getString(R.string.question2), listOf(getString(R.string.opsi_a2), getString(R.string.opsi_b2), getString(R.string.opsi_c2), getString(R.string.opsi_d2)), 0),
            Question(getString(R.string.question3), listOf(getString(R.string.opsi_a3), getString(R.string.opsi_b3), getString(R.string.opsi_c3), getString(R.string.opsi_d3)), 1),
            Question(getString(R.string.question4), listOf(getString(R.string.opsi_a4), getString(R.string.opsi_b4), getString(R.string.opsi_c4), getString(R.string.opsi_d4)), 0),
            Question(getString(R.string.question5), listOf(getString(R.string.opsi_a5), getString(R.string.opsi_b5), getString(R.string.opsi_c5), getString(R.string.opsi_d5)), 1)
        )

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0)
            score = savedInstanceState.getInt("score", 0)
        }

        updateQuestion()

        binding.previousButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                updateQuestion()
            }
        }

        binding.nextButton.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                updateQuestion()
            } else {
                val intent = Intent(this, HasilSkor::class.java)
                intent.putExtra("SCORE", score)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentQuestionIndex", currentQuestionIndex)
        outState.putInt("score", score)
    }

    private fun updateQuestion() {
        val currentQuestion = questions[currentQuestionIndex]

        binding.questionText.text = currentQuestion.question
        binding.soalText.text = getString(R.string.Soal) + " " + (currentQuestionIndex + 1)

        resetOptions()

        binding.optionA.apply {
            text = currentQuestion.options[0]
            setOnClickListener { if (currentQuestion.userSelectedAnswer == null) checkAnswer(0) }
        }
        binding.optionB.apply {
            text = currentQuestion.options[1]
            setOnClickListener { if (currentQuestion.userSelectedAnswer == null) checkAnswer(1) }
        }
        binding.optionC.apply {
            text = currentQuestion.options[2]
            setOnClickListener { if (currentQuestion.userSelectedAnswer == null) checkAnswer(2) }
        }
        binding.optionD.apply {
            text = currentQuestion.options[3]
            setOnClickListener { if (currentQuestion.userSelectedAnswer == null) checkAnswer(3) }
        }

        currentQuestion.userSelectedAnswer?.let { selectedAnswerIndex ->
            val correctButtonColor = Color.parseColor("#31511E")
            val wrongButtonColor = Color.parseColor("#C62E2E")

            if (selectedAnswerIndex == currentQuestion.correctAnswerIndex) {
                when (selectedAnswerIndex) {
                    0 -> binding.optionA.setBackgroundColor(correctButtonColor)
                    1 -> binding.optionB.setBackgroundColor(correctButtonColor)
                    2 -> binding.optionC.setBackgroundColor(correctButtonColor)
                    3 -> binding.optionD.setBackgroundColor(correctButtonColor)
                }
            } else {
                when (selectedAnswerIndex) {
                    0 -> binding.optionA.setBackgroundColor(wrongButtonColor)
                    1 -> binding.optionB.setBackgroundColor(wrongButtonColor)
                    2 -> binding.optionC.setBackgroundColor(wrongButtonColor)
                    3 -> binding.optionD.setBackgroundColor(wrongButtonColor)
                }

                when (currentQuestion.correctAnswerIndex) {
                    0 -> binding.optionA.setBackgroundColor(correctButtonColor)
                    1 -> binding.optionB.setBackgroundColor(correctButtonColor)
                    2 -> binding.optionC.setBackgroundColor(correctButtonColor)
                    3 -> binding.optionD.setBackgroundColor(correctButtonColor)
                }
            }

            val textColor = Color.parseColor("#FFFFFF")
            binding.optionA.setTextColor(textColor)
            binding.optionB.setTextColor(textColor)
            binding.optionC.setTextColor(textColor)
            binding.optionD.setTextColor(textColor)
        }
    }

    private fun resetOptions() {
        val defaultColor = Color.parseColor("#E9EFEC")
        val textColor = Color.parseColor("#347928")

        binding.optionA.setBackgroundColor(defaultColor)
        binding.optionA.setTextColor(textColor)

        binding.optionB.setBackgroundColor(defaultColor)
        binding.optionB.setTextColor(textColor)

        binding.optionC.setBackgroundColor(defaultColor)
        binding.optionC.setTextColor(textColor)

        binding.optionD.setBackgroundColor(defaultColor)
        binding.optionD.setTextColor(textColor)
    }

    private fun checkAnswer(selectedIndex: Int) {
        val currentQuestion = questions[currentQuestionIndex]

        if (currentQuestion.userSelectedAnswer != null) {
            return
        }

        val correctIndex = currentQuestion.correctAnswerIndex
        val correctButtonColor = Color.parseColor("#31511E")
        val wrongButtonColor = Color.parseColor("#C62E2E")

        if (selectedIndex == correctIndex) {
            when (selectedIndex) {
                0 -> binding.optionA.setBackgroundColor(correctButtonColor)
                1 -> binding.optionB.setBackgroundColor(correctButtonColor)
                2 -> binding.optionC.setBackgroundColor(correctButtonColor)
                3 -> binding.optionD.setBackgroundColor(correctButtonColor)
            }
            score += 20
        } else {
            when (selectedIndex) {
                0 -> binding.optionA.setBackgroundColor(wrongButtonColor)
                1 -> binding.optionB.setBackgroundColor(wrongButtonColor)
                2 -> binding.optionC.setBackgroundColor(wrongButtonColor)
                3 -> binding.optionD.setBackgroundColor(wrongButtonColor)
            }
            when (correctIndex) {
                0 -> binding.optionA.setBackgroundColor(correctButtonColor)
                1 -> binding.optionB.setBackgroundColor(correctButtonColor)
                2 -> binding.optionC.setBackgroundColor(correctButtonColor)
                3 -> binding.optionD.setBackgroundColor(correctButtonColor)
            }
        }

        currentQuestion.userSelectedAnswer = selectedIndex
        val textColor = Color.parseColor("#FFFFFF")
        binding.optionA.setTextColor(textColor)
        binding.optionB.setTextColor(textColor)
        binding.optionC.setTextColor(textColor)
        binding.optionD.setTextColor(textColor)

        binding.scoreText.text = getString(R.string.score_text, score)
    }
}
