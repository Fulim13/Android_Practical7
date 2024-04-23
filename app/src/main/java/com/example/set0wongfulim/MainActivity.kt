package com.example.set0wongfulim

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.set0wongfulim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reset()
        binding.btnReset.setOnClickListener{ reset() }
        binding.btnSubmit.setOnClickListener{ submit() }
        binding.btnWebsite.setOnClickListener { website() }
    }

    fun toast(s: String){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    fun reset(){
        binding.edtName.text.clear()
        binding.chkScholarship.isChecked = false
        binding.radFemale.isChecked = true
        binding.spnCampus.setSelection(0)
        binding.edtCGPA.text.clear()

        binding.txtOutput.text = ""

        binding.edtName.requestFocus()
    }

    fun submit(){
        //1. Inputs
        val name: String = binding.edtName.text.toString().trim()
        val scholarship: Boolean = binding.chkScholarship.isChecked
        val gender: String = if(binding.radFemale.isChecked) "Female" else "Male"
        val campus: String = binding.spnCampus.selectedItem.toString()
        val cgpa: Double = binding.edtCGPA.text.toString().toDoubleOrNull() ?: -1.0
//        val cgpa: Int = binding.edtCGPA.text.toString().toIntOrNull() ?: -1

        //2. Validations
        if(name == ""){
            toast("Invalid name")
            return
        }

        if(cgpa < 0.0 || cgpa > 4.0){
            toast("Invalid CGPA")
            return
        }

        //3. Calculations
        val award: String
        val prize: Double

        // val only assign one time
        // var can assign multiple times
        //award = "X" // Not problem
        //award = "Y" // Error, Solution: var award = "X"

        when {
            cgpa >= 3.75 -> {award="Distinction"; prize=100.00}
            cgpa >= 2.75 -> {award="Merit"; prize=50.00}
            cgpa >= 2.00 -> {award="Pass"; prize=0.00}
            else -> {award="Fail"; prize=0.00}
        }

        //4. Outputs
        binding.txtOutput.text = """
            Name = $name
            Scholarship = ${if (scholarship) "Yes" else "No"}
            Gender = $gender
            Campus = $campus
            CGPA = ${ "%.4f".format(cgpa) }
            Award = $award
            Prize = RM ${ "%.2f".format(prize) }  // %.0f.format(percentage) %
        """.trimIndent()
    }

    fun website(){
        // Implicit intent
        val uri = Uri.parse("https://example.com")
        val i = Intent(Intent.ACTION_VIEW,uri)
        startActivity(i)
    }


}