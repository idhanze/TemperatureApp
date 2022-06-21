package com.example.temperatureapp.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class RemoveErrorTextWatcher(val layout: TextInputLayout): TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    
    }
    
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
    
    override fun afterTextChanged(s: Editable?) {
        if(s.toString().isNotEmpty()){
            layout.error = null
        }
    }
}