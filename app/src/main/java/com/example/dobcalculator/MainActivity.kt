package com.example.dobcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var txtViewSelectedDate : TextView? = null  //making it var instead of val and aslo privte so that its only going to be useable inside of amin activity and never accessible from another class

    private var txtViewAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonDatePicker : Button = findViewById(R.id.buttonDatePicker)
        txtViewSelectedDate = findViewById(R.id.txtViewSelectedDate)

        txtViewAgeInMinutes = findViewById(R.id.txtViewAgeInMinutes)

        buttonDatePicker.setOnClickListener{
            clickDatePicker()

        }


    }


    private fun clickDatePicker(){

        val myCalender = Calendar.getInstance()     //gives access to things like current calender dates,like year,month,the day of the month
        val year = myCalender.get(Calendar.YEAR)    //here pass in calender year so myCalender get and then it uses the calender and then it retrieve the year for us
        val month = myCalender.get(Calendar.MONTH)  //it does the same as above
        val day = myCalender.get(Calendar.DAY_OF_MONTH) //same as the above lines

        val dpd = DatePickerDialog(this,
            { _, selectedyear, selectedmonth, selecteddayOfMonth ->
                Toast.makeText(this,
                    "Year was $selectedyear, month was ${selectedmonth+1}, day of month was $selecteddayOfMonth",Toast.LENGTH_LONG).show()

                val selectedDate = "$selecteddayOfMonth/${selectedmonth+1}/$selectedyear"

                txtViewSelectedDate?.setText(selectedDate)

                val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

                // .let -> is just an approach to make sure that we only execute this code if the date is in fact not empty , if the date is empty for some reason then we will not execute the code
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000    //dividing by 60000 coz the getTime/Time shows in milli seconds

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes-selectedDateInMinutes

                        txtViewAgeInMinutes?.text = differenceInMinutes.toString()   //coz difference in minutes is a long type so we convert it into string and ? coz its nullable type

                    }

                }

            },          //this is going to pass all the details to us
            year,month,day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis()-86400000
        dpd.show()


    }

}