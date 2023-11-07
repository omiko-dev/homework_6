package com.example.homework_6

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.homework_6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var users: MutableList<User>

    private val emailRegex: Regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        users = mutableListOf()

        addUser()
    }

    private fun addUser(){

        binding.acbAdd.setOnClickListener {

            if (fieldsValidator()) {
                val user: User

                binding.apply {
                    user = User(
                        etFirstName.text.toString(),
                        etLastName.text.toString(),
                        etAge.text.toString().toInt(),
                        etEmail.text.toString()
                    )
                }

                if (users.contains(user)) {
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                } else {
                    users.add(user)
                    Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                    binding.tvActiveUser.text = "Active User: ${users.size}"
                }


            } else {
                binding.tvMessage.text = "Error"
                binding.tvMessage.setTextColor(Color.RED)

                Log.i("omiko", "error")
            }
        }

    }

    private fun fieldsValidator(): Boolean{

        binding.apply {
           if(
               etFirstName.text.toString().isBlank() ||
               etLastName.text.toString().isBlank() ||
               etEmail.text.toString().isBlank() ||
               etAge.text.toString().isBlank()
           ) { return false }

           if(
               !emailRegex.containsMatchIn(etEmail.text.toString())
           ) { return false }
        }

        return true
    }
//
//    private fun userAddedSuccessfully(){
//
//    }
//
//    private fun userAlreadyExists(){
//
//    }

}