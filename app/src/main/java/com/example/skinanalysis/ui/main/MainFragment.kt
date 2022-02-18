package com.example.skinanalysis.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skinanalysis.R
import com.example.skinanalysis.databinding.MainFragmentBinding
import com.example.skinanalysis.others.JavaScriptInterface
import com.example.skinanalysis.others.jsCodeStr
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*


class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModels()
    private var mRequest: PermissionRequest? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment,
            container, false)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.revieveWebView.let { webView ->
            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true
            webSettings.javaScriptCanOpenWindowsAutomatically = true
            webSettings.allowFileAccess = true
            webSettings.setGeolocationEnabled(true)
            webSettings.allowContentAccess = true
            webSettings.mediaPlaybackRequiresUserGesture = false
            webSettings.domStorageEnabled = true

            webView.webChromeClient = object : WebChromeClient() {

                override fun onPermissionRequest(request: PermissionRequest?) {
                    Log.d(TAG, "onPermissionRequest: ${Arrays.toString(request?.resources)}")
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            context, "Permission already granted",
                            Toast.LENGTH_SHORT
                        ).show()
                        request?.grant(request.resources)
                    } else {
                        mRequest = request
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }

                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    Log.d(TAG, "onConsoleMessage: ${consoleMessage?.message()}")
                    return super.onConsoleMessage(consoleMessage)
                }


            }
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d(TAG, "onPageFinished: ")

                    val call = "javascript:$jsCodeStr"
                    webView.evaluateJavascript(call) {
                        Log.d(TAG, "onViewCreated: $it")
                    }
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.d(TAG, "onReceivedError: ${error?.description}")
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Log.d(TAG, "onReceivedHttpError: ${errorResponse?.reasonPhrase}")
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    Log.d(TAG, "shouldOverrideUrlLoading: ${request?.url}")
                    return true
                }
            }
            webView.addJavascriptInterface(JavaScriptInterface(requireContext()), "Android")

            webView.loadUrl(URL)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
            mRequest?.apply { grant(resources) }
            mRequest = null
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
            Log.d(TAG, "requestPermissionLauncher: denied")
        }
    }

    fun readFile(filePath: String): String {
        var result = ""
        try {
            val br = BufferedReader(FileReader(filePath))
            val sb = StringBuilder()
            var line: String? = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }
            result = sb.toString()
        } catch (e: IOException) {
            Log.e(TAG, "readFile: ${e.message}")
        }
        return result
    }

    companion object {
        const val TAG = "MainFragment"
        private const val URL = "https://d38knilzwtuys1.cloudfront.net/revieve-plugin-v4/app.html?partnerId=RNhaZp3SNs&env=test&crossOrigin=1&origin=*"
        private const val OLD_URL = "https://demov4.revieve.com/philips-ipl"
    }
}