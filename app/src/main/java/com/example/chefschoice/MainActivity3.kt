package com.example.chefschoice

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.chefschoice.database.RecipesApplication
import com.example.chefschoice.databinding.ActivityMain3Binding
import com.example.chefschoice.viewmodel.RecipesViewModelFactory
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity3 : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ActivityMain3Binding

    private val viewModel: RecipesViewModel by viewModels() {
        RecipesViewModelFactory((this.application as RecipesApplication).database.favoriteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        if (ContextCompat.checkSelfPermission(
                this@MainActivity3, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@MainActivity3, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)

        //Add observer

    }


    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int,
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue


                  viewModel.getFoodBarcode(scannedValue).toString()
                    Log.i("Neariah", " Scanned Value #1: $scannedValue")

                    lifecycleScope.launch {
                        viewModel.barcode.observe(this@MainActivity3){
                            var product = it.title
                            var message = it.generatedText

                            //runOnUiThread {
                            cameraSource.stop()

                            val dialogBuilder = AlertDialog.Builder(this@MainActivity3)
                            if (message == null){
                                dialogBuilder.setMessage("No description provided.")
                            } else {
                                // set message of alert dialog
                                dialogBuilder.setMessage("$message")
                                    // if the dialog is cancelable
                                    .setCancelable(false)
                            }
                                // positive button text and action
                                //  .setPositiveButton("Scan Again", DialogInterface.OnClickListener {
                                //          dialog, id -> finish()
                                //  })
                                // negative button text and action
                                .setNegativeButton("Close",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        dialog.cancel()
                                        //Added to return to RecipesFragment
                                        finish()
                                    })

                            // create dialog box
                            val alert = dialogBuilder.create()
                            // set title for alert dialog box
                            alert.setTitle("$product")
                            // show alert dialog
                            alert.show()

                        }
                        //  }
                    }


                    //Should not use value outside of viewmodel bc it doesnt get LiveData aka it updates irregularly
                   // var product = viewModel.barcode.value?.title
                   // Log.i("Neariah", " Product #1: $product")

                   // var message = viewModel.barcode.value?.generatedText
                  //  Log.i("Neariah", " Message Value #1: $message")



                }

                /* Show scannedvalue in text
                Toast.makeText(this@MainActivity3,
                            "value- $info",
                            Toast.LENGTH_LONG).show()
                        finish()
                //Don't forget to add this line printing value or finishing activity must run on main thread
                 runOnUiThread {
                        cameraSource.stop()
                        Toast.makeText(this@MainActivity3,
                            "value- $scannedValue",
                            Toast.LENGTH_SHORT).show()
                        finish()
                         }
                    } else {
                    Toast.makeText(this@MainActivity3, "value- else", Toast.LENGTH_SHORT).show()*/

            }

        })
    }

        private fun askForCameraPermission() {
            ActivityCompat.requestPermissions(
                this@MainActivity3,
                arrayOf(android.Manifest.permission.CAMERA),
                requestCodeCameraPermission
            )
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray,
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupControls()
                } else {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            cameraSource.stop()
        }
    }
