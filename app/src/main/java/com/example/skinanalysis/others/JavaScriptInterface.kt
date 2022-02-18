package com.example.skinanalysis.others

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.fragment.app.FragmentActivity
import com.example.skinanalysis.AppData
import com.example.skinanalysis.R
import com.example.skinanalysis.model.AnalysisData
import com.example.skinanalysis.ui.main.MainFragment
import com.example.skinanalysis.ui.summary.SummaryFragment
import com.google.gson.Gson


class JavaScriptInterface(private val context: Context) {

    @JavascriptInterface
    fun onUserWantsToSelectImage() {
        Log.d(MainFragment.TAG, "onUserWantsToSelectImage: ")
    }

    @JavascriptInterface
    fun onUserWantsToOpenCamera() {
        Log.d(MainFragment.TAG, "onUserWantsToOpenCamera: ")
    }

    @JavascriptInterface
    fun onImageAnalysisFinished(data: String) {
        Log.d(MainFragment.TAG, "onImageAnalysisFinished: $data")
        AppData.analysisData = Gson().fromJson(data, AnalysisData::class.java)
        saveToSharedPref(AppData.analysisData!!)
        Log.d(MainFragment.TAG, "onImageAnalysisFinished: ${AppData.analysisData}")
    }

    private fun saveToSharedPref(analysisData: AnalysisData) {
        val sharedPref = (context as FragmentActivity).getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("Base64Image", analysisData.image)
            apply()
        }
    }

    @JavascriptInterface
    fun onPageChange(activeIndex: Int) {
        Log.d(MainFragment.TAG, "onPageChange: $activeIndex")
        if (activeIndex == 3) {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SummaryFragment())
                .commit()
        }
    }

    @JavascriptInterface
    fun onFetchProducts() {
        Log.d(MainFragment.TAG, "onFetchProducts: ")
    }

    @JavascriptInterface
    fun onClose() {
        Log.d(MainFragment.TAG, "onClose: ")
    }

    @JavascriptInterface
    fun onAnalyticsEvent(data: String) {
        Log.d(MainFragment.TAG, "onAnalyticsEvent: $data")
    }
}