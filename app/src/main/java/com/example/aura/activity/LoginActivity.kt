package com.example.aura.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aura.R

class LoginActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var etPassword:EditText
    lateinit var etUsername:EditText
    lateinit var btnLogin:Button
    lateinit var login:TextView
    lateinit var pass:TextView
    lateinit var reg:TextView
    lateinit var imgLogo:ImageView

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences=getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        etPassword=findViewById(R.id.etPassword)
        etUsername=findViewById(R.id.etUsername)
        btnLogin=findViewById(R.id.btnLogin)
        login=findViewById(R.id.login)
        pass=findViewById(R.id.pass)
        reg=findViewById(R.id.reg)
        imgLogo=findViewById(R.id.imgLogo)

        reg.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()){
                val registeredUsername = sharedPreferences.getString("USERNAME", null)
                val registeredPassword = sharedPreferences.getString("PASSWORD", null)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent= Intent(this@LoginActivity, NewActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onClick(v: View?) {
    }
}