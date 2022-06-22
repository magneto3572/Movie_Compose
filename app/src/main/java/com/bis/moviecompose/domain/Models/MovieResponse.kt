package com.example.assignment.data.Models

data class MovieResponse(
    val page: Int,
    val results: List<result>,
    val total_pages: Int,
    val total_results: Int
)