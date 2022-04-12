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

    val regionList = listOf<Region>(
        Region("Адыгея",10882),
        Region("Алтай",11895),
        Region("Алтайский край",11262),
        Region("Амурская обл.",14704),
        Region("Архангельская обл.",14679),
        Region("Астраханская обл.",12274),
        Region("Башкортостан",11009),
        Region("Белгородская обл.",10629),
        Region("Брянская обл.",11934),
        Region("Бурятия",13793),
        Region("Владимирская обл.",12274),
        Region("Волгоградская обл.",10882),
        Region("Вологодская обл.",12781),
        Region("Воронежская обл.",10756),
        Region("Дагестан",11515),
        Region("Еврейская АО",17053),
        Region("Забайкальский край",14805),
        Region("Ивановская обл.",11642),
        Region("Ингушетия",11895),
        Region("Иркутская обл.",13413),
        Region("Кабардино-Балкария",12890),
        Region("Калининградская обл.",13034),
        Region("Калмыкия",11768),
        Region("Калужская обл.",12148),
        Region("Камчатский край",22930),
        Region("Карачаево-Черкесия",11515),
        Region("Карелия",15104),
        Region("Кемеровская обл.",11515),
        Region("Кировская обл.",11262),
        Region("Коми",15368)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        /*binding.tvMainText.text = "[${binding.etOldCount.text}]"*/

        var parentDemandList = mutableMapOf<Int, View>()
        var parentDemand = mutableMapOf<Int, Int?>()

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
                                parentNumber.text = "Взрослый ${i}"
                            }
                            parentDemandList.put(i, newParent)
                            this.binding.llDemandCountContainer.addView(newParent)
                        }
                    }
                    /*binding.tvMainText.text = "${parentDemandList.count()}"*///"[${binding.etOldCount.text}]"

                    //обработка ввода зарплаты родителей
                    parentDemand = mutableMapOf<Int, Int?>()
                    for ((key, value) in parentDemandList) {
                        val parentDemandCertainItem = value.findViewById<TextInputEditText>(R.id.et_parentDemand)
                        parentDemandCertainItem.addTextChangedListener{
                            var parentDemandCount = parentDemandCertainItem.text.toString()
                            parentDemand.put(key, parentDemandCount.toIntOrNull())
                            binding.tvMainText.text = binding.tvMainText.text.toString() + "\nвзрослый ${key} получает ${parentDemand[key]}"

                        }
                        //id Всех элементов отобразить
                        /*binding.tvMainText.text = binding.tvMainText.text.toString() + value.id*/
                    }

                    /*binding.tvMainText.text. = "${parentDemandList.forEach{(key, value) ->
                        key

                    }}"*/
                }
            }
        }

        binding.bCalculation.setOnClickListener {
            binding.tvMainText.text = "Параметры вашей семьи:"
            addTextToMainTextView("\nФамилия вашей семьи: ${binding.etFamilyName.text.toString()}")
            addTextToMainTextView("\nРегион проживания вашей семьи: ${binding.etRegion.text.toString()}")
            addTextToMainTextView("\nСостав вашей семьи:")
            for ((key, value) in parentDemandList) {
                addTextToMainTextView("взрослый ${key} получает ${parentDemand[key]}")
            }
            addTextToMainTextView("\nКоличество детей: ${binding.etChildren.text.toString()}")
            addTextToMainTextView("\nСтоимость аренды жилья: ${binding.etRentalHousing.text.toString()}")
            addTextToMainTextView("\nСтоимость коммунальных услуг: ${binding.etHouseService.text.toString()}")
            addTextToMainTextView("\nКоличество праздников: ${binding.etEvents.text.toString()}")
            addTextToMainTextView("\nСумма сбережений: ${binding.etSavings.text.toString()}")
            addTextToMainTextView("\nЗапланирована крупная покупка: ${binding.etMajorPurchase.text.toString()}")
            addTextToMainTextView("\nУ членов вашей семьи есть проблемы со здоровьем: ${binding.etHealthProblems.text.toString()}")
            addTextToMainTextView("\nУ вашей семьи есть страховка: ${binding.etInsurance.text.toString()}")
            addTextToMainTextView("\nУ вашей семьи запланирован ребенок: ${binding.etBabyPlan.text.toString()}")
            addTextToMainTextView("\n\nРЕШЕНИЕ ПО ВАШЕЙ СЕМЬЕ:")



            binding.llMainContentContainer.removeAllViews()
        }
        setContentView(binding.root)
    }

    fun addTextToMainTextView(addableTextString: String){
        binding.tvMainText.text = binding.tvMainText.text.toString() + "\n" + addableTextString
    }
}