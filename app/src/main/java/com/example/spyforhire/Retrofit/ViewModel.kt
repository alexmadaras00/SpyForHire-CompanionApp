package com.example.spyforhire.Retrofit

import androidx.lifecycle.ViewModel

class ViewModel: ViewModel() {
    var num=0
    fun counter()
    {
        ++num
    }
}