package com.app.fcm.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.fcm.R
import com.app.fcm.databinding.FragmentHomeBinding
import com.app.fcm.room.User
import com.app.fcm.room.UserViewModel
import com.app.fcm.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val userViewModel: UserViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private var user: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        view.post {
            Toast.makeText(requireContext(), "HomeFragment Loaded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI() {
        userViewModel.allUsers.observe(requireActivity()) { users ->
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
        binding.btNavigate.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment("Hardik")
            findNavController().navigate(action)
        }
    }
}