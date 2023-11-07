package com.example.homework_6

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.homework_6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var users: MutableList<User>
    private val emailRegex: Regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    private var deletedUserCounter: Int = 0
    private var userCount: Int = 0
    private var updateAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        users = mutableListOf()

        addUser()
        removeUser()
        updateUser()

        binding.ivClear.setOnClickListener {
            clearUserInputs()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addUser(){

        binding.acbAdd.setOnClickListener {

            if (fieldsValidator()) {
                val user: User = getUser()

                if (users.contains(user)) {
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                    messageTextChange(false)
                } else {
                    users.add(user)
                    Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                    binding.tvActiveUser.text = "Active User: ${users.size}"
                    messageTextChange(true)
                    //clear inputs
                    clearUserInputs()
                    userCount = users.size-1
                }
            } else {
                Toast.makeText(this, "Enter all Field Correct!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun removeUser(){

        binding.acbRemove.setOnClickListener {
            if(fieldsValidator()) {
                val user: User = getUser()

                if(users.contains(user)){
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
                    // delete user
                    users.remove(user)
                    //update "Active User" and "deleted User"
                    messageTextChange(true)

                    ++deletedUserCounter
                    binding.apply {
                        tvDeletedUser.text = "Deleted User: $deletedUserCounter"
                        tvActiveUser.text = "Active User: ${users.size}"
                    }
                    //clear inputs
                    clearUserInputs()
                    userCount = users.size-1
                }else {
                    Toast.makeText(this, "User does not exits", Toast.LENGTH_SHORT).show()
                    messageTextChange(false)
                }
            }
        }

    }

    private fun updateUser(){
        binding.acbUpdate.setOnClickListener {

            if(users.isNotEmpty()) {
                    if(userCount == -1)
                        userCount = users.size - 1

                    if(updateAvailable && getUser() !in users){
                        users[(userCount + 1) % users.size] = getUser()
                        Log.i("takotako","$userCount ${getUser().email}")
                        clearUserInputs()
                        updateAvailable = false
                    }else{
                        updateAvailable = true
                        setUpdatedFields()
                        userCount--
                    }
            }
        }
    }


    private fun setUpdatedFields(){
        binding.apply {
            (etFirstName as TextView).text = users[userCount].firstName
            (etLastName as TextView).text = users[userCount].lastName
            (etAge as TextView).text = users[userCount].age.toString()
            (etEmail as TextView).text = users[userCount].email
        }
    }

    private fun messageTextChange(correct: Boolean){
        if(correct){
            binding.tvMessage.setText(R.string.success)
            binding.tvMessage.setTextColor(Color.GREEN)
        }else{
            binding.tvMessage.setText(R.string.error)
            binding.tvMessage.setTextColor(Color.RED)
        }
    }

    private fun getUser(): User{
        binding.apply {
            return User(
                etFirstName.text.toString(),
                etLastName.text.toString(),
                etAge.text.toString().toInt(),
                etEmail.text.toString()
            )
        }
    }

    private fun clearUserInputs(){
        binding.apply {
            etFirstName.text.clear()
            etLastName.text.clear()
            etAge.text.clear()
            etEmail.text.clear()
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


}