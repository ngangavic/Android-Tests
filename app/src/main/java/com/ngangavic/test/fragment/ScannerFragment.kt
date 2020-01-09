package com.ngangavic.test.fragment


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.ngangavic.test.R
import java.io.IOException


class ScannerFragment : Fragment() {

    companion object {
        fun newInstance() = ScannerFragment()
    }

    lateinit var surfaceView: SurfaceView
    internal var intentData = ""
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null
    lateinit var root: View
    private val CAMERA_PERMISSION = 201

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.scanner_layout, container, false)
        surfaceView = root.findViewById(R.id.surfaceView)

        barcodeDetector = BarcodeDetector.Builder(root.context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build()

        cameraSource = CameraSource.Builder(root.context, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build()
        initialiseDetectorsAndSources()
        return root
    }

    override fun onResume() {
        super.onResume()
        initialiseDetectorsAndSources()
    }


    override fun onStart() {
        super.onStart()
        initialiseDetectorsAndSources()
    }

    private fun initialiseDetectorsAndSources() {
//        Snackbar.make(fragment_scanner, "Barcode scanner started", Snackbar.LENGTH_SHORT).show()
//        Toast.makeText(root.context, "Barcode scanner started", Toast.LENGTH_SHORT).show()

        barcodeDetector = BarcodeDetector.Builder(root.context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build()

        cameraSource = CameraSource.Builder(root.context, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build()

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                                    root.context,
                                    Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource!!.start(surfaceView.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                                root.context as Activity,
                                arrayOf(Manifest.permission.CAMERA),
                                CAMERA_PERMISSION
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource!!.stop()
            }
        })

        barcodeDetector!!.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
//                Toast.makeText(
//                    root.context,
//                    "To prevent memory leaks barcode scanner has been stopped",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    intentData = barcodes.valueAt(0).displayValue
                    vibratePhone()
                    Log.d("CODE", intentData)
                    barcodeDetector?.release()
                    //cameraSource?.release()
                }
            }

        })
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(100)
        }
    }

}
