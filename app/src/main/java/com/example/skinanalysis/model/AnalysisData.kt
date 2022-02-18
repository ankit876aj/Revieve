package com.example.skinanalysis.model

data class AnalysisData(
    val findings: List<Finding>,
    val hasErrors: Boolean,
    val image: String,
    val warnings: List<Warning>
)