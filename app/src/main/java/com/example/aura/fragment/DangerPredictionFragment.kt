package com.example.aura.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.aura.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class DangerPredictionFragment : Fragment() {
    private lateinit var inputRape: EditText
    private lateinit var inputKidnap: EditText
    private lateinit var inputDowry: EditText
    private lateinit var btnPredict: Button
    private lateinit var txtResult: TextView

    private var interpreter: Interpreter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interpreter = loadModel(requireContext())
        interpreter?.let { interp ->
            val inputTensor = interp.getInputTensor(0)
            val inputShape = inputTensor.shape() // e.g. [1, 21] or [21]
            val inputType = inputTensor.dataType()
            android.util.Log.d("ModelInfo", "Input tensor shape: ${inputShape.contentToString()}, data type: $inputType")

            val outputTensor = interp.getOutputTensor(0)
            val outputShape = outputTensor.shape()
            val outputType = outputTensor.dataType()
            android.util.Log.d("ModelInfo", "Output tensor shape: ${outputShape.contentToString()}, data type: $outputType")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        interpreter?.close()
        interpreter = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_danger_prediction, container, false)

        inputRape = view.findViewById(R.id.inputRape)
        inputKidnap = view.findViewById(R.id.inputKidnap)
        inputDowry = view.findViewById(R.id.inputDowry)
        btnPredict = view.findViewById(R.id.btnPredict)
        txtResult = view.findViewById(R.id.txtResult)

        btnPredict.setOnClickListener {
            val rape = inputRape.text.toString().toFloatOrNull() ?: 0f
            val kidnap = inputKidnap.text.toString().toFloatOrNull() ?: 0f
            val dowry = inputDowry.text.toString().toFloatOrNull() ?: 0f

            val maxRape = 100f
            val maxKidnap = 100f
            val maxDowry = 100f

            val rapeNorm = rape / maxRape
            val kidnapNorm = kidnap / maxKidnap
            val dowryNorm = dowry / maxDowry

            val inputArray = floatArrayOf(
                rapeNorm, kidnapNorm, dowryNorm,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f
            )

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                val result = predictDangerLevel(inputArray)
                withContext(Dispatchers.Main) {
                    txtResult.text = "Predicted Danger Level: $result"
                }
            }
        }

        return view
    }

    private fun loadModel(context: Context): Interpreter {
        val assetFileDescriptor = context.assets.openFd("danger_model.tflite")
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        inputStream.close()
        assetFileDescriptor.close()
        return Interpreter(modelBuffer)
    }

    private fun predictDangerLevel(inputFeatures: FloatArray): String {
        val inputBuffer = ByteBuffer.allocateDirect(4 * inputFeatures.size).order(ByteOrder.nativeOrder())
        for (f in inputFeatures) {
            inputBuffer.putFloat(f)
        }
        inputBuffer.rewind()  // Rewind before running model!

        val outputBuffer = ByteBuffer.allocateDirect(4 * 3).order(ByteOrder.nativeOrder())
        interpreter?.run(inputBuffer, outputBuffer)

        outputBuffer.rewind()  // rewind before reading output
        val probs = FloatArray(3)
        outputBuffer.asFloatBuffer().get(probs)
        val maxIndex = probs.indices.maxByOrNull { probs[it] } ?: -1
        val sortedProbs = probs.sortedDescending()
        val maxProb = sortedProbs[0]
        val secondMaxProb = sortedProbs[1]
        android.util.Log.d("PredictionDebug", "MaxIndex: $maxIndex, MaxProb: $maxProb, SecondMaxProb: $secondMaxProb")
        return if (maxProb - secondMaxProb < 0.02f) {  // smaller threshold, e.g. 0.02
            "Medium"
        } else {
            when (maxIndex) {
                0 -> "Low"
                1 -> "Medium"
                2 -> "High"
                else -> "Unknown"
            }
        }
    }
}