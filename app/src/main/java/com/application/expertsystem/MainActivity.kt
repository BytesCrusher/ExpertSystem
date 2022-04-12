package com.application.expertsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.application.expertsystem.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        /*binding.tvMainText.text = "[${binding.etOldCount.text}]"*/

        var parentDemandList = mutableMapOf<Int, View>()

        binding.etOldCount.addTextChangedListener {
            binding.llDemandCountContainer.removeAllViews()
            parentDemandList = mutableMapOf<Int, View>()//обнуление мапы с зарплатами родителей
            binding.tvMainText.text = ""//обнуление вьюшки с текстом

            val count = binding.etOldCount.text.toString()
            if (count != "") {
                val countInt:Int? = count.toIntOrNull()
                if (countInt != null){
                    if (countInt > 0) {

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
                            parentDemandList.put(i, newParent)
                            this.binding.llDemandCountContainer.addView(newParent)
                        }
                    }
                    /*binding.tvMainText.text = "${parentDemandList.count()}"*///"[${binding.etOldCount.text}]"

                    //обработка ввода зарплаты родителей
                    for ((key, value) in parentDemandList) {
                        val parentDemandCertainItem = value.findViewById<TextInputEditText>(R.id.et_parentDemand)
                        /*parentDemandCertainItem.setBackgroundColor(getResources().getColor(R.color.purple_500))*/
                        parentDemandCertainItem.addTextChangedListener{
                            binding.tvMainText.text = binding.tvMainText.text.toString() + parentDemandCertainItem.text.toString()

                        }

                        binding.tvMainText.text = binding.tvMainText.text.toString() + value.id
                    }

                    /*binding.tvMainText.text. = "${parentDemandList.forEach{(key, value) ->
                        key

                    }}"*/
                }
            }
        }
        setContentView(binding.root)
    }
}