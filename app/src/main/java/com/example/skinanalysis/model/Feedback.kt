package com.example.skinanalysis.model

import com.google.gson.annotations.SerializedName

enum class Feedback {

    @SerializedName("imageAnalysis.score.excellent")
    EXCELLENT,
    @SerializedName("imageAnalysis.score.good")
    GOOD,
    @SerializedName("imageAnalysis.score.ok")
    OK
}