package skin.lesion.detection

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.graphics.scale
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class TFLiteHelper(context: Context) {

    private val labels = listOf("akiec", "bcc", "bkl", "df", "mel", "nv", "vasc")
    private val interpreter: Interpreter

    companion object {
        private const val MODEL_NAME = "skin_lesion_model.tflite"
        private const val INPUT_SIZE = 224
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

        return Interpreter(
            fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        )
    }

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val resizedBitmap = bitmap.scale(INPUT_SIZE, INPUT_SIZE)
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
        resizedBitmap.getPixels(intValues, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)

        for (pixel in intValues) {
            val r = ((pixel shr 16) and 0xFF) / 255f
            val g = ((pixel shr 8) and 0xFF) / 255f
            val b = (pixel and 0xFF) / 255f
            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }

        byteBuffer.rewind()
        return byteBuffer
    }

    fun predict(bitmap: Bitmap): String {
        val byteBuffer = preprocessImage(bitmap)

        val output = Array(1) { FloatArray(labels.size) }

        interpreter.run(byteBuffer, output)

        Log.d("TFLiteOutput", output[0].joinToString())

        val maxIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
        return labels[maxIndex]
    }

}
