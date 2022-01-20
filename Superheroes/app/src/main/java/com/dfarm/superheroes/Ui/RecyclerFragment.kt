package com.dfarm.superheroes.Ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dfarm.superheroes.Adapter.ContactModel
import com.dfarm.superheroes.Adapter.MainAdapter
import com.dfarm.superheroes.R
import com.dfarm.superheroes.databinding.FragmentRecyclerBinding
import java.util.*
import kotlin.collections.ArrayList


class RecyclerFragment : Fragment(),TextToSpeech.OnInitListener {

    private var _binding: FragmentRecyclerBinding? = null
    val binding get() = _binding!!
    var tts: TextToSpeech? = null
    // val recyclerView = binding.recVw

    val arrayList = ArrayList<ContactModel>()
    // val adapter  = MainAdapter(requireContext(),arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tts = TextToSpeech(requireContext(), this)
//         checkPermission()
        getPhoneContacts()

//        val dialog = AlertDialog.Builder(requireContext())
//            .setMessage("Creating Workspace")
//            .setView(R.layout.progress_dialog_layout)
//            .create()
//        dialog.show()

    }

    private fun speakOut(text: String) {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }



    @SuppressLint("Range")
    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireContext() as Activity,
                arrayOf(android.Manifest.permission.READ_CONTACTS), 0)
        }
        else {
            //getContactList()
            getPhoneContacts()
        }
    }

    @SuppressLint("Range")
    fun getContactList() {
        val sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        val contentResolver = activity?.applicationContext?.contentResolver
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor = contentResolver?.query(uri, null, null, null,sort)
        if(cursor?.count!! > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                val selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?"
                val phoneCursor = contentResolver.query(uriPhone, null, selection, arrayOf(id), null)

                if(phoneCursor?.moveToNext() == true) {
                    val number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val model =  ContactModel()
                    model.name = name
                    model.number = number
                    arrayList.add(model)
                    phoneCursor.close()
                }
            }
            cursor.close()
        }


        val layoutManager = LinearLayoutManager(requireContext())
        binding.recVw.layoutManager = layoutManager

        val adapter = MainAdapter(requireContext(), arrayList)

        binding.recVw.adapter = adapter
        binding.recVw.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 0 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //getContactList()
            getPhoneContacts()
        }
        else {
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            checkPermission()
        }
    }






    @SuppressLint("Range")
    private fun getPhoneContacts() {

        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireContext() as Activity,
                arrayOf(android.Manifest.permission.READ_CONTACTS), 0)
        }

            val sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            val contentResolver = activity?.applicationContext?.contentResolver
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val cursor = contentResolver?.query(uri, null, null, null, sort)
            Log.i("CONTACT_PROVIDER_DEMO", "TOTAL # of Contacts ::: ${cursor?.count.toString()}")
            if (cursor?.count!! > 0) {
                while (cursor.moveToNext()) {
                    val contactName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val contactNumber =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val model = ContactModel()
                    model.name = contactName
                    model.number = contactNumber
                    arrayList.add(model)

                    Log.i("CONTACT_PROVIDER_DEMO",
                        "Contact Name  $contactName  Number $contactNumber")
                }
            }

            val layoutManager = LinearLayoutManager(requireContext())
            binding.recVw.layoutManager = layoutManager

            val adapter = MainAdapter(requireContext(), arrayList)
        adapter.clkPos.observe(viewLifecycleOwner){
            val item = arrayList[it]
            if(flag == 1)
            speakOut(item.name)
        }
            binding.recVw.adapter = adapter
            binding.recVw.addItemDecoration(
                DividerItemDecoration(requireContext(), layoutManager.orientation)
            )
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

}