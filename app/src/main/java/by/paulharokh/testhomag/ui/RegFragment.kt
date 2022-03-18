package by.paulharokh.testhomag.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import by.paulharokh.testhomag.R
import by.paulharokh.testhomag.databinding.FragmentRegBinding
import by.paulharokh.testhomag.remote.ApiRequest
import by.paulharokh.testhomag.remote.Reg
import by.paulharokh.testhomag.viewmodels.RegViewModel
import kotlinx.coroutines.launch
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class RegFragment : Fragment() {
    private var _regBinding: FragmentRegBinding? = null
    private val regBinding get() = _regBinding!!
    private val vmReg: RegViewModel by activityViewModels()
    private val vmRegAnswer: RegViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _regBinding = FragmentRegBinding.inflate(inflater, container, false)
        return regBinding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regBinding.layBack.setOnClickListener {
            view.findNavController().navigate(R.id.startFragment)
        }

        regBinding.tvEntry.setOnClickListener {
            view.findNavController().navigate(R.id.authFragment)
        }

        fun makeEtMask(mask: String, et: EditText) {
            val slots = UnderscoreDigitSlotsParser().parseSlots(mask)
            val formatWatcher: FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
            )
            formatWatcher.installOn(et)
        }

        makeEtMask("+_ ___ ___ __ __", regBinding.etPhone)
        makeEtMask("__.__.____", regBinding.etBirthday)






        regBinding.rbFemale.setOnClickListener {
            regBinding.rbMale.isChecked = false
        }

        regBinding.rbMale.setOnClickListener {
            regBinding.rbFemale.isChecked = false
        }

        regBinding.rbAgreement.setOnClickListener {
            regBinding.btnEntry.isEnabled = true
            if (regBinding.btnEntry.isEnabled) {
                regBinding.btnEntry.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.blue))
            }
        }

        regBinding.btnEntry.setOnClickListener {

            val gender: String = when {
                regBinding.rbMale.isChecked -> {
                    "Мужской"
                }
                regBinding.rbFemale.isChecked -> {
                    "Женский"
                }
                else -> {
                    ""
                }
            }

            val etArr = arrayOf(
                regBinding.etFullName.text.toString(),
                gender,
                regBinding.etBirthday.text.toString(),
                regBinding.etPhone.text.toString(),
                regBinding.etEmail.text.toString(),
                regBinding.etCompany.text.toString(),
                regBinding.etService.text.toString(),
                regBinding.etLogin.text.toString(),
                regBinding.etPassword.text.toString(),
                regBinding.etPassword2.text.toString()
            )

            var notEmpty = true
            for (i in etArr.indices) {
                if (etArr[i].isEmpty()) {
                    notEmpty = false
                    break
                }
            }


            if (!notEmpty) {
                Toast.makeText(
                    activity?.applicationContext,
                    "Заполните все данные для регистрации!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val data = Reg(
                    etArr[0],
                    etArr[1],
                    etArr[2],
                    etArr[3],
                    etArr[4],
                    etArr[5],
                    etArr[6],
                    etArr[7],
                    etArr[8],
                    etArr[9]
                )

                vmReg.regData.value = data




                viewLifecycleOwner.lifecycleScope.launch {
                    val apiRequest = ApiRequest.create()

                    try {


                        val request = apiRequest.postReg(vmReg.regData.value!!)
                        vmRegAnswer.regResponse.value = request
                    } catch (e: Exception) {
                        Log.d("!!!", e.toString())
                    }

                    if (vmRegAnswer.regResponse.value?.STATUS == true) {


                        val dialogueBuilder = AlertDialog.Builder(activity!!)
                            .setMessage("«Регистраци успешна. ID —" + " " + vmRegAnswer.regResponse.value?.USER_ID)
                            .setPositiveButton("Ok") { _, _ ->
                                view.findNavController().navigate(R.id.startFragment)
                                val imm =
                                    context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view.windowToken, 0)
                            }

                        val dialog = dialogueBuilder.create()
                        dialog.show()
                    } else {
                        val dialogueBuilder = AlertDialog.Builder(activity!!)
                            .setMessage(vmRegAnswer.regResponse.value?.MESSAGE)
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
