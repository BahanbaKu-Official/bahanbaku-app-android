package com.bangkit.bahanbaku.presentation.cookingguide

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.core.adapter.CookingGuideStepsListAdapter
import com.bangkit.bahanbaku.core.adapter.RecipeDetailStepsListAdapter
import com.bangkit.bahanbaku.core.adapter.StepIngredientGridListAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.RecipeDetailItem
import com.bangkit.bahanbaku.databinding.ActivityCookingGuideBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.task.audio.classifier.AudioClassifier
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

@AndroidEntryPoint
class CookingGuideActivity : AppCompatActivity() {

    private val binding: ActivityCookingGuideBinding by lazy {
        ActivityCookingGuideBinding.inflate(layoutInflater)
    }

    private val viewModel: CookingGuideViewModel by viewModels()

    var modelPath = "lanjut_lalu_v1.tflite"
    var probabilityThreshold: Float = 0.9f
    private var token: String? = null
    private lateinit var id: String
    private val step = MutableLiveData(1)
    private lateinit var recipeData: RecipeDetailItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getStringExtra(EXTRA_ID) ?: ""
        getToken()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            modelSetup()
        }
    }

    private fun setupView(token: String) {
        viewModel.getRecipeById(token, id).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    val data = result.data
                    recipeData = data as RecipeDetailItem
                }
            }
        }

        step.observe(this) {
            if (this::recipeData.isInitialized) {
                if (it <= recipeData.steps.size) {
                    val data = recipeData.steps[it - 1]
                    binding.tvNumber.text = it.toString()
                    binding.tvStep.text = data.step

                    binding.rvStepIngredients.apply {
                        adapter = StepIngredientGridListAdapter(data.ingredients)
                        layoutManager = GridLayoutManager(this@CookingGuideActivity, 3)
                    }

                    binding.rvInstructions.apply {
                        adapter = CookingGuideStepsListAdapter(
                            recipeData.steps.subList(
                                it - 1,
                                recipeData.steps.size - (it - 1)
                            ), it
                        )
                        layoutManager = LinearLayoutManager(this@CookingGuideActivity)
                    }
                }
            }
        }
    }

    private fun getToken() {
        viewModel.getToken().observe(this) {
            if (it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                token = "Bearer $it"
                setupView(token!!)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun modelSetup() {
        var COUNTER = 0
        val REQUEST_RECORD_AUDIO = 1337
        requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)

        // TODO 2.3: Loading the model from the assets folder
        val classifier = AudioClassifier.createFromFile(this, modelPath)

        // TODO 3.1: Creating an audio recorder
        val tensor = classifier.createInputTensorAudio()

        // TODO 3.2: showing the audio recorder specification
        val format = classifier.requiredTensorAudioFormat

        // TODO 3.3: Creating
        val record = classifier.createAudioRecord()
        record.startRecording()

        Timer().scheduleAtFixedRate(1, 500) {

            // TODO 4.1: Classifing audio data
            val numberOfSamples = tensor.load(record)
            val output = classifier.classify(tensor)

            // TODO 4.2: Filtering out classifications with low probability
            val filteredModelOutput = output[0].categories.filter {
                it.score > probabilityThreshold && it.label != "background"
            }

            // TODO 4.3: Creating a multiline string with the filtered results
            val outputStr =
                filteredModelOutput.sortedBy { -it.score }
                    .joinToString(separator = "\n") { "${it.label} -> ${it.score} " }

            // TODO 4.4: Updating the UI
            if (outputStr.isNotEmpty()) {
                if (this@CookingGuideActivity::recipeData.isInitialized) {
                    step.postValue(step.value?.plus(1) ?: 1)
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}