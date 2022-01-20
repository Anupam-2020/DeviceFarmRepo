package com.dfarm.superheroes.Ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dfarm.superheroes.Adapter.HomeAdapter
import com.dfarm.superheroes.Adapter.OfflineAdapter
import com.dfarm.superheroes.Adapter.RecyclerItemOperation
import com.dfarm.superheroes.R
import com.dfarm.superheroes.Repository.localRepository.LocalImageLoadingRepository
import com.dfarm.superheroes.Repository.remoteRepository.RemoteImageLoadingRepository
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails
import com.dfarm.superheroes.ViewModels.HospitalListViewModel
import com.dfarm.superheroes.databinding.FragmentHospitalListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HospitalListFragment : Fragment(), TextToSpeech.OnInitListener {

    private val scp : CoroutineScope = CoroutineScope(Dispatchers.IO)

    var tts: TextToSpeech? = null

    private val viewModel : HospitalListViewModel by viewModels()

    @Inject
    lateinit var localRepository : LocalImageLoadingRepository

    @Inject
    lateinit var remoteRepository: RemoteImageLoadingRepository

    private var _binding : FragmentHospitalListBinding? = null


    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        _binding = FragmentHospitalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userprofile.setOnClickListener {
            if(flag == 1) {
                speakOut("Way to user fragment")
            }
            findNavController().navigate(R.id.userFragment)
        }

        if(isConnectedToInternet()) {
            initRecycler()
        }
        else {
            offlineData()
            Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_SHORT).show()
        }

        tts = TextToSpeech(requireContext(), this)

    }

    private fun speakOut(text: String) {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }


    private fun isConnectedToInternet(): Boolean {
        val connectivityManager: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return  connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }


    private fun initRecycler() {

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recView.layoutManager = layoutManager

        binding.recView.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        viewModel.saveRemoteToLocalDb()
        viewModel.loadImg()

            fetchImagesFromSqLite()
//            fetchImagesFromServer()
        
    }

    private fun fetchImagesFromSqLite() {
        scp.launch {
            val result =  localRepository.getImageData()
            withContext(Dispatchers.Main) {
                val data = result
                Log.i("LocalData","${data}")
                val adapter = HomeAdapter(
                    requireContext(),
                    data,
                )
                binding.recView.adapter = adapter
                adapter.clkPos.observe(viewLifecycleOwner){
                    val item = viewModel.getImages()[it]
                    if(flag == 1) {
                        speakOut(item.name)
                    }
                    findNavController().navigate(HospitalListFragmentDirections.actionHospitalListFragmentToDescriptionFragment(item.id))
                }
            }
        }
    }

    private fun offlineData() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recView.layoutManager = layoutManager

        binding.recView.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )

        val data = listOf(
            ImageDetails(2,"","","","","","")
        )
        val adapter = OfflineAdapter(
            requireContext(),
            data
        )
        binding.recView.adapter = adapter
    }



    private fun fetchImagesFromServer() {

        scp.launch {
                val result = remoteRepository.getImageData()

            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    val data = result.getOrThrow()
                    data.map {
                        ImageDetails(
                            id = it.id,
                            name = it.name,
                            desc = it.desc,
                            image = it.image,
                            doctors = it.doctors,
                            location = it.location,
                            address = it.address
                        )
                        Log.i("@Check", "Operation Success")
                        val adapter = HomeAdapter(
                            requireContext(),
                            data,
                        )
                        binding.recView.adapter = adapter
                        adapter.clkPos.observe(viewLifecycleOwner) {
                            val item = viewModel.getImages()[it]
                            if(flag == 1) {
                                speakOut(item.name)
                            }
                            findNavController().navigate(HospitalListFragmentDirections.actionHospitalListFragmentToDescriptionFragment(
                                item.id))
                        }
                    }
                } else {
                    Log.i("@Check", "Operation Failed")
                }
            }
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
}
