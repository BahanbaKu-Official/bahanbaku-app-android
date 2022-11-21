package com.bangkit.bahanbaku.presentation.cookingguide

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.CookingGuideIngredientsGridAdapter
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
    var probabilityThreshold: Float = 0.96f
    private var token: String? = null
    private lateinit var id: String
    private val step = MutableLiveData(1)
    private lateinit var recipeData: RecipeDetailItem
    private val recipe = MutableLiveData<RecipeDetailItem>()
    private val isMicOn = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBarCookingGuide)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        id = intent.getStringExtra(EXTRA_ID) ?: ""
        getToken()

        modelSetup()
    }

    private fun setupView(token: String) {
        viewModel.getRecipeById(token, id).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    val data = result.data as RecipeDetailItem
                    recipeData = data
                    recipe.postValue(data)
                    supportActionBar?.title = data.title
                }
            }
        }

        recipe.observe(this) {
            step.observe(this) {
                if (this::recipeData.isInitialized) {
                    if (it <= recipeData.steps.size) {
                        val data = recipeData.steps[it - 1]
                        binding.layoutCookingGuideStep.apply {
                            tvNumber.text = it.toString()
                            tvStep.text = data.step
                        }

                        binding.rvCookingGuideIngredients.apply {
                            adapter = CookingGuideIngredientsGridAdapter(data.ingredients)
                            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                        }
                    }
                }
            }
        }

        binding.fabNext.setOnClickListener {
            if (this::recipeData.isInitialized && step.value!! < recipeData.steps.size) {
                step.postValue(step.value?.plus(1))
            }
        }

        binding.fabPrevious.setOnClickListener {
            if (this::recipeData.isInitialized && step.value!! > 1) {
                step.postValue(step.value?.minus(1))
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

        Timer().scheduleAtFixedRate(1, 1000) {

            // TODO 4.1: Classifing audio data
            val numberOfSamples = tensor.load(record)
            val output = classifier.classify(tensor)

            // TODO 4.2: Filtering out classifications with low probability
            val filteredModelOutput = output[0].categories.filter {
                it.score > probabilityThreshold && it.label != "background"
            }

            // TODO 4.4: Updating the UI
            if (filteredModelOutput.isNotEmpty()) {
                if (filteredModelOutput.sortedBy { it.score }[0].label == "lanjut") {
                    if (this@CookingGuideActivity::recipeData.isInitialized) {
//                        step.postValue(step.value?.plus(1) ?: 1)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toogla_mic, menu)
        isMicOn.observe(this) { micOn ->
            if (micOn) {
                menu.findItem(R.id.mic).icon = AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_baseline_mic_24
                )
            } else {
                menu.findItem(R.id.mic).icon = AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_baseline_mic_off_24
                )
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mic -> {
//                isMicOn.postValue(!(isMicOn.value as Boolean))
                Toast.makeText(
                    this,
                    "Voice command feature will be coming very soon!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}