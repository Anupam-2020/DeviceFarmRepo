package com.dfarm.superheroes.Ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dfarm.superheroes.R
import com.dfarm.superheroes.databinding.FragmentUserBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class UserFragment : Fragment(),TextToSpeech.OnInitListener {

    var tts: TextToSpeech? = null

    var counter by Delegates.notNull<Int>()

    private val REQUEST_IMAGE_CAPTURE = 1

    lateinit var currentPhotoPath: String

    private lateinit var photoFile: File

    private lateinit var photoFileSdCard: File

    private var _binding : FragmentUserBinding? = null

    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tts = TextToSpeech(requireContext(), this)
        binding.button3.setOnClickListener {
            if(flag == 1)
            speakOut(binding.button3.text.toString())
            findNavController().navigate(R.id.loginFragment)
        }

        binding.camBtn.setOnClickListener{
            if(flag == 1)
            speakOut("Camera Button")
            dispatchTakePictureIntent()
            counter = 1
            // openCamera()

        }
        binding.button2.setOnClickListener {
            if(flag == 1)
            speakOut(binding.button2.text.toString())
            openCamera()
            counter = 2
        }

        binding.imageView2.setOnClickListener {
            // getPhoneContacts()
            if(flag == 1)
            speakOut("Way to contacts")
//            findNavController().navigate(R.id.recyclerFragment)
            checkPermission()

        }

        binding.capturedImg.setOnClickListener {
            if(flag == 1)
            speakOut("Captured Image")
        }

//        findNavController().navigate(R.id.loginFragment)

    }


    private fun speakOut(text: String) {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         photoFile = createImageFile()
        try {
            photoFile.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.dfarm.superheroes.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        } catch (e: ActivityNotFoundException) {
            Log.i("@Error","${e}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(counter == 1) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                val imageBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                Log.i("@Path", "$photoFile")
                binding.capturedImg.setImageBitmap(imageBitmap)
            }
        }
        else if(counter == 2){
            val selectImage = data?.data
            // val selectImage = BitmapFactory.decodeFile(photoFile.absolutePath)
//            lateinit var inputStream: InputStream
//            try {
//                assert(selectImage != null)
//                inputStream = selectImage?.let { context?.contentResolver?.openInputStream(selectImage) }!!
//            }
//            catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            }
//            BitmapFactory.decodeStream(inputStream)
            binding.capturedImg.setImageURI(selectImage)
        }

//        if(resultCode == REQUEST_IMAGE_CAPTURE) {
//            val selectImage = data?.data
//            // val selectImage = BitmapFactory.decodeFile(photoFile.absolutePath)
//            lateinit var inputStream: InputStream
//            try {
//                assert(selectImage != null)
//                inputStream = selectImage?.let { context?.contentResolver?.openInputStream(selectImage) }!!
//            }
//            catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            }
//            BitmapFactory.decodeStream(inputStream)
//            binding.capturedImg.setImageURI(selectImage)
//        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    fun openCamera() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
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

    @SuppressLint("Range")
    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireContext() as Activity,
                arrayOf(android.Manifest.permission.READ_CONTACTS), 0)
        }
        else {
            findNavController().navigate(R.id.recyclerFragment)
        }
    }


//    @SuppressLint("Range")
//    private fun getPhoneContacts() {
//
//        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_CONTACTS)
//            != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(requireContext() as Activity,
//                arrayOf(android.Manifest.permission.READ_CONTACTS), 0)
//        }
//
//        val contentResolver = activity?.applicationContext?.contentResolver
//        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
//        val cursor = contentResolver?.query(uri, null, null, null, null)
//        Log.i("CONTACTS","TOTAL Contacts    ${cursor?.count.toString()}")
//        if(cursor?.count!! > 0) {
//            while (cursor.moveToNext()) {
//                val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                val contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//
//                Log.i("CONTACTS","Contact Name  $contactName  Number $contactNumber")
//            }
//        }
//    }

}