package com.app.fcm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.fcm.databinding.ActivityMainBinding
import com.app.fcm.room.User
import com.app.fcm.room.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val binding by viewBindingWithSetContentView(ActivityMainBinding::inflate)
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRoom()
    }

    private fun initRoom() {
        userViewModel.allUsers.observe(this) { users ->
            println("users: ${users.size}")
            if (users.isNotEmpty()) {
                user = users.last()
                println("Id: ${user!!.id}, User: ${user!!.name}, Email: ${user!!.email}, Age: ${user!!.age}")
            }
        }
        binding.btGetUser.setOnClickListener {
            userViewModel.getUser()
        }
        binding.btGetUsers.setOnClickListener {
            userViewModel.getUsers()
        }
        binding.btAddUser.setOnClickListener {
            userViewModel.insert(User(name = "John", email = "john@yopmail.com", age = 32))
        }
        binding.btAddUsers.setOnClickListener {
            userViewModel.insert(listOf(User(name = "John", email = "john@yopmail.com", age = 32)))
        }
        binding.btDeleteUser.setOnClickListener {
            user?.let { it1 -> userViewModel.delete(it1) }
        }
    }
}