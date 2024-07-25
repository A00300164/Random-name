package com.hanson.sanjeev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hanson.sanjeev.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    //Frontend XML Binding Object Declaration (Sign Up Frontend / Registration)
    private lateinit var binding: ActivitySignUpBinding

    //Database Object Declaration
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Actual Sign Up Frontend XML Binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Actual Database Object Definition
        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        //SIGN UP Logic - SIGN UP Button click code
        binding.button.setOnClickListener {
            //Read All Text Fields
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            //Verify if all text fields are not empty
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {

                //Verify both passwords fields are same
                if (pass == confirmPass) {
                    //Fire query on Database -- Create User Query
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, SignInActivity::class.java)

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
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                //Toast Error Message
                Toast.makeText(this, "Empty fields are not allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}