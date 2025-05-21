package com.example.bmicalculator

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.round
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Initializations

        val edtWeight: EditText = findViewById(R.id.edtWeight)
        val edtCms: EditText = findViewById(R.id.edtHeight)
        val edtFeet: EditText = findViewById(R.id.edtFeet)
        val edtInch: EditText = findViewById(R.id.edtInch)
        val btnCalculate: androidx.appcompat.widget.AppCompatButton = findViewById(R.id.btnCalculate)
        val spHeight: Spinner = findViewById(R.id.spHeight)
        val spWeight: Spinner = findViewById(R.id.spWeight)
        val bmiResult: TextView = findViewById(R.id.bmiResult)
        var result: Double = 0.0
        var main: LinearLayout = findViewById(R.id.main)
        var toolbar: com.google.android.material.appbar.MaterialToolbar = findViewById(R.id.toolbar)


        //top app bar


        toolbar.setOnMenuItemClickListener { menuItem ->

            when (menuItem.itemId) {
                R.id.night_option -> {main.setBackgroundColor(ContextCompat.getColor(this, R.color.night_color))

                true}

                else -> {main.setBackgroundColor(ContextCompat.getColor(this, R.color.light_color))
                true}

            }

        }

        //spinners

        spHeight.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                edtFeet.visibility = View.GONE
                edtInch.visibility = View.GONE
                edtCms.visibility = View.GONE


                if(p2==0){
                    edtCms.visibility = View.VISIBLE
                    edtFeet.visibility = View.GONE
                    edtInch.visibility = View.GONE
                }
                else{
                    edtCms.visibility = View.GONE
                    edtFeet.visibility = View.VISIBLE
                    edtInch.visibility = View.VISIBLE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        spWeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p2==0){
                    edtWeight.hint = "Enter weight in kgs"
                }
                else{
                    edtWeight.hint = "Enter weight in pounds"
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        //calculate button

        btnCalculate.setOnClickListener {

            var weight:Double? = null
            var height:Double? = null

            if (edtWeight.text.toString().isEmpty()) {
                edtWeight.error="Please enter the weight"
                return@setOnClickListener
            }
            if(spHeight.selectedItemPosition ==0){
                if (edtCms.text.toString().isEmpty()) {
                    edtCms.error="Please enter the Cms"
                    return@setOnClickListener
                }
            }
            else{
                if(edtFeet.text.toString().isEmpty()) {
                    edtFeet.error="Please enter the Fts"
                }
                if(edtInch.text.toString().isEmpty()) {
                    edtInch.error="Please enter the Inch"
                }
            }


        //Calculations

            if(spWeight.selectedItemPosition==0){
                weight = edtWeight.text.toString().toDouble()

            }
            else{
                weight = edtWeight.text.toString().toDouble() * 0.453592

            }

            if(spHeight.selectedItemPosition==0){
                height = edtCms.text.toString().toDouble()
            }
            else{
                height = (edtFeet.text.toString().toDouble() * 30.48) + (edtInch.text.toString().toDouble() * 2.54)
            }





            result = (weight / (height * height * 0.0001))
            result = round(result * 100) / 100

            if (result < 18.5) {
                bmiResult.text = "Your BMI is $result\n\nUnderweight"
                bmiResult.textAlignment = View.TEXT_ALIGNMENT_CENTER
                main.setBackgroundColor(ContextCompat.getColor(this, R.color.backUW))
            } else if (result > 25) {
                bmiResult.text = "Your BMI is $result\n\nOverweight"
                bmiResult.textAlignment = View.TEXT_ALIGNMENT_CENTER
                main.setBackgroundColor(ContextCompat.getColor(this, R.color.backOW))
            } else {
                bmiResult.text = "Your BMI is $result\n\nNormal"
                bmiResult.textAlignment = View.TEXT_ALIGNMENT_CENTER
                main.setBackgroundColor(ContextCompat.getColor(this, R.color.backH))
            }


        }
    }
}