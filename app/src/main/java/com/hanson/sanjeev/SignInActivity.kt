package com.hanson.sanjeev

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hanson.sanjeev.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    //Frontend XML Binding Object Declaration (Sign In Frontend)
    private lateinit var binding: ActivitySignInBinding

    //Database Object Declaration
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Actual Sign In Frontend XML Binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Actual Database Object Definition
        firebaseAuth = FirebaseAuth.getInstance() //DB Obj

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        //SIGN IN Logic - SIGN IN Button click code
        binding.button.setOnClickListener {
            //Read Both Text Fields
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            //Verify if both text fields are not empty
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                //Sign In Using Email and Password -- Fire query on Database -- Sing In Query
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)

                        //Welcome toast message
                        Toast.makeText(this, "Welcome! $email", Toast.LENGTH_SHORT).show()

                        //Redirect to Main Activity
                        startActivity(intent)
                    } else {
                        //Toast Error Message
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                //Toast Error Message
                Toast.makeText(this, "Empty fields are not allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}