package com.application.expertsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.application.expertsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.etOldCount.addTextChangedListener {
            binding.llDemandCountContainer.removeAllViews()

            //null safety надо допилить
            val count = binding.etOldCount.getText().toString()
            val countInt:Int? = count?.toInt()
            if (countInt != null && countInt > 0) {
                for (i in 1..countInt) {
                    val newParent = layoutInflater.inflate(
                        R.layout.item_demand_element,
                        this.binding.llDemandCountContainer,
                        false
                    )
                    newParent.id = i
                    newParent.apply {
                        this.id = i
                        val parentNumber = this.findViewById<TextView>(R.id.tv_parent)
                        parentNumber.text = "Родитель ${i}"
                    }
                    this.binding.llDemandCountContainer.addView(newParent)
                }
            }
        }
        setContentView(binding.root)
    }
}