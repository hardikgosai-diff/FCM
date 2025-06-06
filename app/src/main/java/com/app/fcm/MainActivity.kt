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
        println("onCreate")
        initRoom()
    }

    private fun initRoom() {
        userViewModel.allUsers.observe(this) { users ->
            if (users.isNotEmpty()) {
                user = users.last()
                println("Id: ${user!!.id}, User: ${user!!.name}, Email: ${user!!.email}, Age: ${user!!.age}")
            } else println("empty")
        }

        binding.btGetUser.setOnClickListener {
            userViewModel.getUser()
        }
        binding.btAddUser.setOnClickListener {
            userViewModel.insert(User(name = "John", email = "john@yopmail.com", age = 25))
        }
        binding.btDeleteUser.setOnClickListener {
            user?.let { it1 -> userViewModel.delete(it1) }
        }
    }
}