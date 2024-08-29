package com.mir.testermodule.presentation.speechmatchpage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mir.testermodule.R
import java.util.Locale

class SpeechMatchActivity : AppCompatActivity() {

    private lateinit var viewModel: SpeechMatchViewModel
    private lateinit var speechRecognizer: SpeechRecognizer

    private lateinit var btnStartListening: Button
    private lateinit var tvPrompt: TextView
    private lateinit var tvVoice: TextView
    companion object {
        private const val RECORD_AUDIO_PERMISSION_CODE = 1
    }

    // Google's Speech Recognition Launch
    private val speechRecognitionResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        val recognizedText = matches?.firstOrNull()

        tvVoice.text = recognizedText

    if (recognizedText != null) {
        if (recognizedText.equals(/*targetPhrase*/tvPrompt.text.toString(), ignoreCase = true)) {
            // Handle success - speech matches targetPhrase
            tvPrompt.setTextColor(Color.GREEN)
        } else {
            // Handle failure - speech doesn't match targetPhrase
            tvPrompt.setTextColor(Color.RED)
        }
    }
        // Re-enable the button after processing
        btnStartListening.isEnabled = true
    }

    // AUDIO Permission
    private fun checkPermissionAndSetupSpeechRecognizer() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        } else {
            // Permission has already been granted
            setupSpeechRecognizer()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Companion.RECORD_AUDIO_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with SpeechRecognizer setup
                    setupSpeechRecognizer()
                } else {
                    // Permission denied, handle accordingly
                    // For example, display a message or disable speech recognition functionality
                }
                return
            }
            // Handle other permission requests if needed
        }
    }

    // Speech Recognizer
    private fun setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.e("SHITABUGGER", "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                Log.e("SHITABUGGER", "onBeginningOfSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.e("SHITABUGGER", "onRmsChanged $rmsdB")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.e("SHITABUGGER", "onBufferReceived")
            }

            override fun onEndOfSpeech() {
                Log.e("SHITABUGGER", "onEndOfSpeech")
            }

            override fun onError(error: Int) {
                Log.e("SHITABUGGER", "onError $error")

                viewModel.onStartButtonClicked() // stopping it
                viewModel.isListening = false
//                startListening() // Restart listening after receiving results

            }

            override fun onResults(results: Bundle){
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    viewModel.updateDisplayedText(matches[0])
                }
                startListening() // Restart listening after receiving results
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.e("SHITABUGGER", "onPartialResults")
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    viewModel.updateDisplayedText(matches[0])
                }
                Log.e("SHITABUGGER", "onPartialResults matches size: ${matches?.size}")

            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.e("SHITABUGGER", "onEvent")

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_speech_match)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[SpeechMatchViewModel::class.java]
        btnStartListening = findViewById(R.id.btnStartListening)
        tvPrompt = findViewById(R.id.tvPrompt)
        tvVoice = findViewById(R.id.tvVoice)

        setObservers()
        checkPermissionAndSetupSpeechRecognizer()

    // ------------------------- With Google's Voice Prompt -----------------------------------
        btnStartListening.setOnClickListener {
            btnStartListening.isEnabled = false // Disable the button during listening

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say the phrase \"${tvPrompt.text.toString()}\"")
            }

            speechRecognitionResultLauncher.launch(intent)

            // Reactivate the button after 3 seconds delay
            btnStartListening.postDelayed({
                btnStartListening.isEnabled = true
            }, 3000)
        }
    // --------------------------------------------------------------------------------------------


    // ------------------------- Without Google's Voice Prompt -----------------------------------
        /*btnStartListening.setOnClickListener {
            Log.e("SHITABUGGER", "startButton.setOnClickListener")
            Toast.makeText(this, "Clicked!!!", Toast.LENGTH_LONG).show()
            if (!viewModel.isListening) {
                startListening()
            } else {
                stopListening()
            }
            viewModel.onStartButtonClicked()
        }*/
    // --------------------------------------------------------------------------------------------

    }


    private fun setObservers() {
        // Observer for displayedText
        val displayedTextObserver = Observer<String> { newText ->
            // Update UI with the new text
            // For example, update a TextView
            tvVoice.text = newText
        }
        viewModel.displayedText.observe(this, displayedTextObserver)

        // Observer for buttonText
        val buttonTextObserver = Observer<String> { newText ->
            // Update the text of the button
            btnStartListening.text = newText
        }
        viewModel.buttonText.observe(this, buttonTextObserver)
    }

    private fun startListening() {
        Log.e("SHITABUGGER", "startListening")

        speechRecognizer.startListening(createRecognizerIntent())
    }

    private fun stopListening() {
        Log.e("SHITABUGGER", "stopListening")

        speechRecognizer.stopListening()
    }

    private fun createRecognizerIntent(): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        return intent
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }

}
