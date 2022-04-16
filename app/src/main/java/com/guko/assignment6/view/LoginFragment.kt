package com.guko.assignment6.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.guko.assignment6.R
import com.guko.assignment6.data.Client
import com.guko.assignment6.databinding.FragmentLoginBinding

import com.guko.assignment6.model.UserAuth
import com.guko.assignment6.retrofit.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private var username: String = ""
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initListeners()
        isUserLoggedIn()
        binding.btnLogIn.setOnClickListener {
            login()
        }
        binding.btnGoSign.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }

    private fun login() {

        username = binding.etName.text.toString()
        password = binding.etPassword.text.toString()

        NetworkHelper().userService?.login(username, password)
            ?.enqueue(object : Callback<UserAuth> {
                override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {

                    val status = response.body()?.status.toString()
                    val errorMsg = response.body()?.message.toString()

                    if (status == "true") {
                        with(Client(requireContext())) {
                            this.setUserEmail(response.body()?.user!![0].email)
                            this.setUserGender(response.body()?.user!![0].gender)
                            this.setUserUsername(response.body()?.user!![0].username)
                            this.setRememberMe(true)
                        }
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    } else
                        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            })

    }
    private fun initListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })
    }

    private fun checkFields() {
        if (!binding.etName.text.isNullOrEmpty() && !binding.etPassword.text.isNullOrEmpty()) {
            binding.btnLogIn.isEnabled = true
            binding.btnLogIn.alpha = 1F
        } else {
            binding.btnLogIn.isEnabled = false
            binding.btnLogIn.alpha = 0.2F
        }
    }
    private fun isUserLoggedIn() {
        if (Client(requireContext()).isRememberMe()) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }
}



