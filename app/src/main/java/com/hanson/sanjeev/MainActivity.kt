package com.hanson.sanjeev

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.util.nextAlphanumericString
import com.hanson.sanjeev.databinding.ActivityMainBinding
import kotlin.random.Random
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    //Frontend XML Binding Object Declaration (Main Activity)
    private lateinit var binding: ActivityMainBinding

    //Database Object Declaration
    private lateinit var database: DatabaseReference

//    private lateinit var adapter: RandomNameAdapter
    private val randomNames = mutableListOf<RandomName>()

    //Arrays for names to generate random name on button click
    private val wrestlers = listOf("Hulk Hogan", "The Rock", "Stone Cold Steve Austin", "John Cena", "Andre the Giant", "Ric Flair", "Triple H", "Shawn Michaels", "The Undertaker", "Bret Hart", "Randy Savage", "Macho Man Randy Savage", "Ultimate Warrior", "Kurt Angle", "Booker T", "Chris Jericho", "Big Show", "Edge", "Rey Mysterio", "CM Punk")
    private val pokemon = listOf("Pikachu", "Charizard", "Bulbasaur", "Squirtle", "Jigglypuff", "Gengar", "Mewtwo", "Eevee", "Snorlax", "Dragonite", "Lucario", "Greninja", "Lapras", "Gyarados", "Machamp", "Alakazam", "Vaporeon", "Jolteon", "Flareon", "Mew")
    private val rappers = listOf("Eminem", "Drake", "Jay-Z", "Kanye West", "Tupac", "Notorious B.I.G.", "Nas", "Lil Wayne", "Snoop Dogg", "Ice Cube", "Kendrick Lamar", "J. Cole", "Travis Scott", "Post Malone", "Cardi B", "Nicki Minaj", "Dr. Dre", "50 Cent", "Migos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Actual Main Activity Frontend XML Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Actual Database Object Definition
        database = FirebaseDatabase.getInstance().reference

        enableEdgeToEdge()
        setContentView(binding.root)

        // Set up RecyclerView
//        adapter = RandomNameAdapter(this, randomNames)
//        binding.listView.adapter = adapter

        //Dropdown list
        val spinner: Spinner = binding.spinnerNameCat

        val categoriesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )

        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = categoriesAdapter

        //Sign out logic - Display toast message and close the application
        binding.butClose.setOnClickListener {
            Toast.makeText(this, "Thank you!", Toast.LENGTH_SHORT).show()
            finish()
        }

        //Generate Names Button Click Logic
        binding.butGenerateNames.setOnClickListener {
            //Read selected list item
            val selectedCategory = spinner.selectedItem.toString()

            val randomName = when (selectedCategory) {
                "Wrestlers" -> wrestlers.random()
                "Pokémon" -> pokemon.random()
                "Rappers" -> rappers.random()
                else -> Toast.makeText(this, "Select the proper category.", Toast.LENGTH_SHORT).show()
            }

            if(selectedCategory != "Select...") {
                //Display generated random name on text box
                binding.txtNames.text = randomName.toString()

                //Call function to save values on Database
                saveNameToFirebase(randomName.toString(), selectedCategory)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        database.child("randomNames").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                randomNames.clear()
//                for (data in snapshot.children) {
//                    val randomName = data.getValue(RandomName::class.java)
//                    if (randomName != null) {
//                        randomNames.add(randomName)
//                    }
//                }
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle possible errors
//            }
//        })
    }

    //Function body to save selected Category and generated name on the ddatabase
    private fun saveNameToFirebase(randomName: String, selectedCategory: String) {
        val key = database.child("randomNames").push().key
        if (key != null) {
            val randomNameData = mapOf(
                "category" to selectedCategory,
                "name" to randomName
            )
            database.child("randomNames").child(key).setValue(randomNameData)
        }

        //Display toast success message
        Toast.makeText(this, "Data saved.", Toast.LENGTH_SHORT).show()
    }

    // Read data from Firebase and populate the RecyclerView

}