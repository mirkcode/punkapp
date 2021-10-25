/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mvaresedev.punkapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}