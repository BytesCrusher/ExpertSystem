package com.application.expertsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.application.expertsystem.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val regionList = listOf<Region>(
        Region(0, "Адыгея",10882),
        Region(1, "Алтай",11895),
        Region(2,"Алтайский край",11262),
        Region(3,"Амурская обл.",14704),
        Region(4, "Архангельская обл.",14679),
        Region(5, "Астраханская обл.",12274),
        Region(6, "Башкортостан",11009),
        Region(7, "Белгородская обл.",10629),
        Region(8, "Брянская обл.",11934),
        Region(9, "Бурятия",13793),
        Region(10, "Владимирская обл.",12274),
        Region(11, "Волгоградская обл.",10882),
        Region(12, "Вологодская обл.",12781),
        Region(13, "Воронежская обл.",10756),
        Region(14, "Дагестан",11515),
        Region(15, "Еврейская АО",17053),
        Region(16, "Забайкальский край",14805),
        Region(17, "Ивановская обл.",11642),
        Region(18, "Ингушетия",11895),
        Region(19, "Иркутская обл.",13413),
        Region(20, "Кабардино-Балкария",12890),
        Region(21, "Калининградская обл.",13034),
        Region(22, "Калмыкия",11768),
        Region(23, "Калужская обл.",12148),
        Region(24, "Камчатский край",22930),
        Region(25, "Карачаево-Черкесия",11515),
        Region(26, "Карелия",15104),
        Region(27, "Кемеровская обл.",11515),
        Region(28, "Кировская обл.",11262),
        Region(29, "Коми",15368)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        var parentDemandList = mutableMapOf<Int, View>()
        var parentDemand = mutableMapOf<Int, Int?>()

        binding.etOldCount.addTextChangedListener {
            binding.llDemandCountContainer.removeAllViews()
            parentDemandList = mutableMapOf<Int, View>()//обнуление мапы с зарплатами родителей

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

                    //обработка ввода зарплаты родителей
                    parentDemand = mutableMapOf<Int, Int?>()
                    for ((key, value) in parentDemandList) {
                        val parentDemandCertainItem = value.findViewById<TextInputEditText>(R.id.et_parentDemand)
                        parentDemandCertainItem.addTextChangedListener{
                            var parentDemandCount = parentDemandCertainItem.text.toString()
                            parentDemand.put(key, parentDemandCount.toIntOrNull())
                        }
                    }
                }
            }
        }

        //адаптер для выбора региона
        val regions = mutableListOf<String>()
        regionList.forEach { regions.add(it.regionName) }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, regions)
        binding.etRegion.setAdapter(adapter)
        // Минимальное число символов для показа выпадающего списка
        binding.etRegion.threshold = 1
        // Обработчик щелчка
        binding.etRegion.onItemClickListener = AdapterView.OnItemClickListener { parent, _,
                                                                                 position, id ->
            val selectedRegion = parent.getItemAtPosition(position).toString()
            // Выводим выбранное слово
            Toast.makeText(applicationContext, "Выбран регион: $selectedRegion", Toast.LENGTH_SHORT).show()
        }

        //адаптер для проблем со здоровьем
        val yesNoList = listOf<String>("Да", "Нет")
        val healthAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yesNoList)
        binding.etHealthProblems.setAdapter(healthAdapter)

        //адаптер для страховки
        val insuranceAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yesNoList)
        binding.etInsurance.setAdapter(insuranceAdapter)
        binding.etInsurance.onItemClickListener = AdapterView.OnItemClickListener { parent, _,
                                                                                    position, id ->
            binding.llInsuranceContainer.removeAllViews()
            val selectedItem = parent.getItemAtPosition(position).toString()

            if (selectedItem == "Да"){
                val insuranceCostItem = layoutInflater.inflate(
                    R.layout.item_insurance_cost,
                    this.binding.llDemandCountContainer,
                    false
                )
                insuranceCostItem.apply {
                    /*val parentNumber = this.findViewById<TextView>(R.id.tv_parent)
                    parentNumber.text = "Взрослый ${i}"*/
                }
                this.binding.llInsuranceContainer.addView(insuranceCostItem)
            }
            // Выводим выбранное слово
            Toast.makeText(applicationContext, "Страховка: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        //адаптер для детей
        val babyAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yesNoList)
        binding.etBabyPlan.setAdapter(babyAdapter)

        //листнер на кнопку начала расчетов
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

    //функция добавления текста в главный лейбл чтобы выводить результат
    fun addTextToMainTextView(addableTextString: String){
        binding.tvMainText.text = binding.tvMainText.text.toString() + "\n" + addableTextString
    }

    //функция проведения расчета
    fun calculate(){

    }
}