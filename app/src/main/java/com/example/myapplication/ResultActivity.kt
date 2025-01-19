package com.example.myapplication
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("SCORE", 0)
        val nom = intent.getStringExtra("nom")

        val utilisateur =findViewById<TextView>(R.id.textview_name)
        val textViewResult = findViewById<TextView>(R.id.textview_score)
        val restartButton = findViewById<Button>(R.id.restart_button)
        val shareButton = findViewById<Button>(R.id.share_button)
        val finishButton = findViewById<Button>(R.id.finish_button)

        textViewResult.text = "Votre score est : $score"
        utilisateur.text = "$nom"

        restartButton.setOnClickListener {
            restartGame()
        }

        shareButton.setOnClickListener {
            if (nom != null) {
                shareScore(nom, score)
            }
        }

        finishButton.setOnClickListener {
            finish()
        }



    }
    private fun restartGame() {
        val intent = Intent(this, QuestionsActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun shareScore(nom:String, score: Int) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "$nom a obtenu un score de $score ! Pouvez-vous faire mieux ?"
            )
        }
        startActivity(Intent.createChooser(shareIntent, "Partager mon score avec"))
    }

}
