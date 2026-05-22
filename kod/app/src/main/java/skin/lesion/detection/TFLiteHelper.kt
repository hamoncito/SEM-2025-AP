package skin.lesion.detection

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.scale
import org.tensorflow.lite.Interpreter
import skin.lesion.detection.models.LesionType
import skin.lesion.detection.models.PredictionResult
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class TFLiteHelper(context: Context) {

    private val labels = listOf(
        "ACK",
        "BCC",
        "MEL",
        "NEV",
        "SCC",
        "SEK"
    )


    private val interpreter: Interpreter

    companion object {
        private const val MODEL_NAME = "skin_lesion_model.tflite"
        private const val INPUT_SIZE = 299
    }

    init {
        interpreter = loadModel(context)
    }

    private fun loadModel(context: Context): Interpreter {
        val assetFileDescriptor = context.assets.openFd(MODEL_NAME)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val options = Interpreter.Options().apply {
            setNumThreads(4)
        }

        return Interpreter(
            fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength), options
        )
    }

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val resizedBitmap = bitmap.scale(INPUT_SIZE, INPUT_SIZE)
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
        resizedBitmap.getPixels(intValues, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)

        for (pixel in intValues) {
            val r = ((pixel shr 16) and 0xFF).toFloat()
            val g = ((pixel shr 8) and 0xFF).toFloat()
            val b = (pixel and 0xFF).toFloat()

            byteBuffer.putFloat(b - 103.939f)
            byteBuffer.putFloat(g - 116.779f)
            byteBuffer.putFloat(r - 123.68f)
        }

        byteBuffer.rewind()
        return byteBuffer
    }


    fun predict(bitmap: Bitmap): List<PredictionResult> {
        val byteBuffer = preprocessImage(bitmap)
        val output = Array(1) { FloatArray(labels.size) }

        interpreter.run(byteBuffer, output)

        val predictions = output[0].mapIndexed { index, confidence ->
            val lesionType = LesionType.mapToLesionType(labels[index])
            PredictionResult(lesionType, confidence)
        }

        return predictions.sortedByDescending { it.confidence }
    }
}
