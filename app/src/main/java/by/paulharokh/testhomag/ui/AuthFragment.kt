package by.paulharokh.testhomag.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import by.paulharokh.testhomag.R
import by.paulharokh.testhomag.databinding.FragmentAuthBinding
import by.paulharokh.testhomag.remote.ApiRequest
import by.paulharokh.testhomag.remote.Auth
import by.paulharokh.testhomag.viewmodels.AuthViewModel
import kotlinx.coroutines.launch


class AuthFragment : Fragment() {
    private var _authBinding: FragmentAuthBinding? = null
    private val authBinding get() = _authBinding!!
    private val vmAuth: AuthViewModel by activityViewModels()
    private val vmAuthAnswer: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _authBinding = FragmentAuthBinding.inflate(inflater, container, false)
        return authBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authBinding.layBack.setOnClickListener {
            view.findNavController().navigate(R.id.startFragment)
        }

        authBinding.tvReg.setOnClickListener {
            view.findNavController().navigate(R.id.regFragment)
        }

        authBinding.btnEntry.setOnClickListener {
            var notEmpty = true

            if (authBinding.etFullName.text.toString().isEmpty() ||
                authBinding.etPass.text.toString().isEmpty()
            ) {
                notEmpty = false
            }

            if (!notEmpty) {
                Toast.makeText(
                    activity?.applicationContext,
                    "Заполните все данные для входа!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val data = Auth(
                    login = authBinding.etFullName.text.toString(),
                    password = authBinding.etPass.text.toString(),
                )

                vmAuth.authData.value = data




                viewLifecycleOwner.lifecycleScope.launch {
                    val apiRequest = ApiRequest.create()

                    try {


                    val request = apiRequest.postAuth(vmAuth.authData.value!!)
                    vmAuthAnswer.authResponse.value = request}
                    catch (e:Exception){
                        Log.d("!!!",e.toString())
                    }

                    if (vmAuthAnswer.authResponse.value?.status == true) {



                        val dialogueBuilder = AlertDialog.Builder(activity!!)
                            .setMessage("«Авторизация успешна. ID —" + " " + vmAuthAnswer.authResponse.value?.id)
                            .setPositiveButton("Ok") { _, _ ->
                                view.findNavController().navigate(R.id.startFragment)
                                val imm =
                                    context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view.windowToken, 0)
                            }

                        val dialog = dialogueBuilder.create()
                        dialog.show()
                    } else
                    {
                        val dialogueBuilder = AlertDialog.Builder(activity!!)
                            .setMessage(vmAuthAnswer.authResponse.value?.message)
                            .setPositiveButton("Ok") { _, _ ->
                                view.findNavController().navigate(R.id.startFragment)
                                val imm =
                                    context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view.windowToken, 0)

                            }

                        val dialog = dialogueBuilder.create()
                        dialog.show()
                    }
                }


            }


        }


    }


}
