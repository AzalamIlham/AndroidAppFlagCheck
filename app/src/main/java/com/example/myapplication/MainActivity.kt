package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button_start)
        val userNameInput: TextView = findViewById(R.id.name)
        button.setOnClickListener {
            val userName = userNameInput.text.toString()
            if (userName.isNotEmpty()) {
                val intent = Intent(this, QuestionsActivity::class.java)
                intent.putExtra("USER_NAME", userName)
                startActivity(intent)
                finish()
            } else {
                userNameInput.error = "Veuillez entrer votre nom"
                Toast.makeText(this, "Le champ nom est obligatoire", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
