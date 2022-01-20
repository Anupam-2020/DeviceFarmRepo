package com.dfarm.superheroes.Ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dfarm.superheroes.MainActivity
import com.dfarm.superheroes.R
import com.dfarm.superheroes.databinding.FragmentLoginBinding
import java.util.*
import java.util.concurrent.Executor

var flag = 0
class LoginFragment : Fragment(), TextToSpeech.OnInitListener {

    var tts: TextToSpeech? = null
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var _binding : FragmentLoginBinding? = null
    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login()
        biometricAuthentication()
        langPreference()
        tts = TextToSpeech(requireContext(), this)



        binding.textView.setOnClickListener {

            if(flag == 1) {
                speakOut(binding.textView.text.toString())
            }

        }
//        binding.LoginId.setOnClickListener {
//            if(binding.LoginId.toString().isEmpty()) {
//                Toast.makeText(requireContext(),"No text found",Toast.LENGTH_SHORT).show()
//            }
//            else {
//                speakOut(binding.LoginId.text.toString())
//            }
//        }

        binding.switch1?.setOnClickListener {
            flag = if(flag == 0) 1 else 0
            speakOut(binding.switch1!!.text.toString())
        }


        binding.userEdt.setOnClickListener {
                //speakOut("Enter Username")
            if(flag == 1){
            speakOut(binding.userEdt.hint.toString())
            }
        }


        binding.passEdt.setOnClickListener {
            //speakOut("Enter Password")
            if (flag == 1) {
                speakOut(binding.passEdt.hint.toString())
            }
        }

        binding.textView3.setOnClickListener {
            if (flag == 1) {
                speakOut(binding.textView3.text.toString())
            }
        }

    }

    private fun speakOut(text: String) {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }


//    private fun login(){
//        val usr = "admin"
//        val pass = "admin"
//        binding.LoginId.setOnClickListener {
//            if(binding.userEdt.text.toString()==usr && binding.passEdt.text.toString()==pass){
//                findNavController().navigate(R.id.hospitalListFragment)
//                //findNavController().navigate(R.id.userFragment)
//                //findNavController().navigate(R.id.mapsFragment)
//            }
//            else if(binding.userEdt.text.toString().contains(".")) {
//                Toast.makeText(requireContext(), "User name can't have .", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                Toast.makeText(requireContext(), "User name Or Password Incorrect", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun login() {
        val usr1 = "tonystark"
        val usr2 = "stevebrooklyn"
        val usr3 = "natwidow"
        val usr4 = "clintbarton"
        val usr5 = "odinson"
        val usr6 = "bighulk"
        val pass = "Kakinada"
        binding.LoginId.setOnClickListener {
            if (isConnectedToInternet()) {
                if (binding.userEdt.text.toString().isEmpty() || binding.passEdt.text.toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Username or Password can't be empty", Toast.LENGTH_SHORT).show()
//                } else if (binding.userEdt.text.toString().contains(".")) {
//                    Toast.makeText(requireContext(), "Username can't contain .", Toast.LENGTH_SHORT)
//                        .show()
//                } else if (binding.passEdt.text.toString().contains(Regex("[a-zA-Z0-9]{0,19}"))) {
//                    findNavController().navigate(R.id.hospitalListFragment)
                } else if (binding.userEdt.text.toString() == usr1 || binding.userEdt.text.toString() == usr2
                    || binding.userEdt.text.toString() == usr3
                    || binding.userEdt.text.toString() == usr4
                    || binding.userEdt.text.toString() == usr5 || binding.userEdt.text.toString() == usr6 && binding.passEdt.text.toString() == pass) {
                    findNavController().navigate(R.id.hospitalListFragment)
                } else {
                    Toast.makeText(requireContext(),
                        "Invalid username or password",
                        Toast.LENGTH_SHORT).show()
                }
                if(flag == 1) {
                    speakOut(binding.LoginId.text.toString())
                }
            }
            else {
                if(flag == 1) {
                    speakOut(binding.LoginId.text.toString())
                }
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return  connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    private fun biometricAuthentication() {
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
//                    Toast.makeText(context,
//                        "Authentication succeeded!", Toast.LENGTH_SHORT)
//                        .show()
                    if (isConnectedToInternet()) {
                        findNavController().navigate(R.id.hospitalListFragment)
                        Toast.makeText(context,
                            "Authentication succeeded!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText(" ")
            .build()

        val biometricLoginButton = binding.BioAut
        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
            //findNavController().navigate(R.id.hospitalListFragment)
            if(flag == 1) {
                speakOut("Biometric Authentication")
            }
        }
    }

    private fun langPreference() {

        val intent = Intent(Intent.ACTION_MAIN)

        currentLanguage = intent.getStringExtra(currentLang).toString()

        binding.txtEng.setOnClickListener{
            setLocale("en")
            speakOut(binding.txtEng.text.toString())
        }
        binding.txtSpa.setOnClickListener{
            setLocale("es")
            speakOut(binding.txtSpa.text.toString())
        }
    }

    fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(
                requireContext(),
                MainActivity::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.i("TTS","Language not supported")
        }
        else {
            Log.i("TTS","Initialization failed")
        }
    }

    override fun onDestroy() {

        if(tts!= null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}

