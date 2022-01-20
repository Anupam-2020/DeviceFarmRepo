package com.dfarm.superheroes.Ui

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dfarm.superheroes.R
import com.dfarm.superheroes.Repository.localRepository.LocalImageLoadingRepository
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails
import com.dfarm.superheroes.ViewModels.HospitalListViewModel
import com.dfarm.superheroes.databinding.FragmentDescriptionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class DescriptionFragment : Fragment(), TextToSpeech.OnInitListener {

    var tts: TextToSpeech? = null
    private val scp: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private var _binding: FragmentDescriptionBinding? = null

    @Inject
    lateinit var localRepository: LocalImageLoadingRepository

    private val binding get() = _binding!!

    private val args: DescriptionFragmentArgs by navArgs()

    private val viewModel: HospitalListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.id
        showDetails(id)
        tts = TextToSpeech(requireContext(), this)
        binding.descHosName.setOnClickListener {
            if(flag == 1) {
                speakOut(binding.descHosName.text.toString())
            }
        }


        binding.desctxt.setOnClickListener {
            if(flag == 1)
            speakOut(binding.desctxt.text.toString())
        }

        binding.textView5.setOnClickListener {
            if(flag == 1)
            speakOut(binding.textView5.text.toString())
        }

        binding.addtxt.setOnClickListener {
            if(flag == 1)
            speakOut(binding.addtxt.text.toString())
        }

        binding.textView7.setOnClickListener {
            if(flag == 1)
            speakOut(binding.textView7.text.toString())
        }

        binding.docListtxt.setOnClickListener {
            if(flag == 1)
            speakOut(binding.docListtxt.text.toString())
        }

        binding.descImg.setOnClickListener {
            if(flag == 1)
            speakOut("Hospital Image")
        }

    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    fun showDetails(id: Int) {
        scp.launch {
            var data: ImageDetails = ImageDetails(
                id = 0,
                name = "default",
                desc = "default",
                image = "default",
                doctors = "default",
                location = "default",
                address = "default"
            )
            val result = localRepository.getImageData()
            withContext(Dispatchers.Main) {
                for (img in result) {
                    if (id == img.id) {
                        data = img
                        break
                    }
                }
                binding.descHosName.text = data.name
                loadWithGlide(binding.descImg, data.image, requireContext())
                //Update here ...........
                binding.desctxt.text = data.desc
                binding.addtxt.text = data.address
                binding.docListtxt.text = data.doctors

                binding.mapImage.setOnClickListener {
                    findNavController().navigate(DescriptionFragmentDirections.actionDescriptionFragmentToMapsFragment(
                        data.location,
                        data.name))
                    if(flag == 1)
                    speakOut("Way to google maps.")

                }

                binding.userFab.setOnClickListener {
                    findNavController().navigate(R.id.userFragment)
                    if(flag == 1)
                    speakOut("Way to user fragment")
                }

            }
        }
    }

    private fun loadWithGlide(img: ImageView, url: String, context: Context) {

        Glide.with(context)
            .load(url)
            .transition(
                DrawableTransitionOptions.withCrossFade(150)
            )
            .into(img)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.i("TTS", "Language not supported")
        } else {
            Log.i("TTS", "Initialization failed")
        }
    }

    override fun onDestroy() {

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


}