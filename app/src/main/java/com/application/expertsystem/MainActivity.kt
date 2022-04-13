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
        Region(0, "Адыгея", 10882),
        Region(1, "Алтай", 11895),
        Region(2, "Алтайский край", 11262),
        Region(3, "Амурская обл.", 14704),
        Region(4, "Архангельская обл.", 14679),
        Region(5, "Астраханская обл.", 12274),
        Region(6, "Башкортостан", 11009),
        Region(7, "Белгородская обл.", 10629),
        Region(8, "Брянская обл.", 11934),
        Region(9, "Бурятия", 13793),
        Region(10, "Владимирская обл.", 12274),
        Region(11, "Волгоградская обл.", 10882),
        Region(12, "Вологодская обл.", 12781),
        Region(13, "Воронежская обл.", 10756),
        Region(14, "Дагестан", 11515),
        Region(15, "Еврейская АО", 17053),
        Region(16, "Забайкальский край", 14805),
        Region(17, "Ивановская обл.", 11642),
        Region(18, "Ингушетия", 11895),
        Region(19, "Иркутская обл.", 13413),
        Region(20, "Кабардино-Балкария", 12890),
        Region(21, "Калининградская обл.", 13034),
        Region(22, "Калмыкия", 11768),
        Region(23, "Калужская обл.", 12148),
        Region(24, "Камчатский край", 22930),
        Region(25, "Карачаево-Черкесия", 11515),
        Region(26, "Карелия", 15104),
        Region(27, "Кемеровская обл.", 11515),
        Region(28, "Кировская обл.", 11262),
        Region(29, "Коми", 15368)
    )
    var parentDemandList = mutableMapOf<Int, View>()
    var parentDemand = mutableMapOf<Int, Int?>()
    var familyDemandCount = 0
    var familyExpensesCount = 0
    var insuranceCost = 0
    var regionLivingWage = 0
    var peopleCount = 0
    var familyDemandByLivingWage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.etOldCount.addTextChangedListener {
            binding.llDemandCountContainer.removeAllViews()
            parentDemandList = mutableMapOf<Int, View>()//обнуление мапы с зарплатами родителей

            val count = binding.etOldCount.text.toString()
            if (count != "") {
                val countInt: Int? = count.toIntOrNull()
                if (countInt != null) {
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
                    familyDemandCount = 0
                    parentDemand = mutableMapOf<Int, Int?>()
                    for ((key, value) in parentDemandList) {
                        val parentDemandCertainItem =
                            value.findViewById<TextInputEditText>(R.id.et_parentDemand)
                        parentDemandCertainItem.addTextChangedListener {
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
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, regions)
        binding.etRegion.setAdapter(adapter)
        // Минимальное число символов для показа выпадающего списка
        binding.etRegion.threshold = 1
        // Обработчик щелчка
        binding.etRegion.onItemClickListener = AdapterView.OnItemClickListener { parent, _,
                                                                                 position, id ->
            val selectedRegion = parent.getItemAtPosition(position).toString()
            // Выводим выбранное слово
            Toast.makeText(applicationContext, "Выбран регион: $selectedRegion", Toast.LENGTH_SHORT)
                .show()
        }

        //адаптер для проблем со здоровьем
        val yesNoList = listOf<String>("Да", "Нет")
        val healthAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yesNoList)
        binding.etHealthProblems.setAdapter(healthAdapter)

        //адаптер для страховки
        val insuranceAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yesNoList)
        binding.etInsurance.setAdapter(insuranceAdapter)
        binding.etInsurance.onItemClickListener = AdapterView.OnItemClickListener { parent, _,
                                                                                    position, id ->
            binding.llInsuranceContainer.removeAllViews()
            insuranceCost = 0
            val selectedItem = parent.getItemAtPosition(position).toString()

            if (selectedItem == "Да") {
                val insuranceCostItem = layoutInflater.inflate(
                    R.layout.item_insurance_cost,
                    this.binding.llDemandCountContainer,
                    false
                )
                insuranceCostItem.apply {
                    val etInsuranceCost = this.findViewById<TextView>(R.id.et_insuranceCost)
                    etInsuranceCost.addTextChangedListener {
                        if (etInsuranceCost.text.toString() != "") {
                            insuranceCost = etInsuranceCost.text.toString().toInt()
                        } else {
                            insuranceCost = 0
                        }
                    }
                }

                this.binding.llInsuranceContainer.addView(insuranceCostItem)
            }
            // Выводим выбранное слово
            Toast.makeText(applicationContext, "Страховка: $selectedItem", Toast.LENGTH_SHORT)
                .show()
        }

        //адаптер для детей
        val babyAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yesNoList)
        binding.etBabyPlan.setAdapter(babyAdapter)

        //листнер на кнопку начала расчетов
        binding.bCalculation.setOnClickListener {
            binding.tvMainText.text = "Параметры вашей семьи:"
            addTextToMainTextView("\nФамилия вашей семьи: ${binding.etFamilyName.text.toString()}")
            addTextToMainTextView("\nРегион проживания вашей семьи: ${binding.etRegion.text.toString()}")

            regionList.forEach {
                if (it.regionName == binding.etRegion.text.toString()) {
                    regionLivingWage = it.livingWage
                }
            }
            addTextToMainTextView("\nПрожиточный минимум для человека в этом регионе: ${regionLivingWage.toString()}")
            addTextToMainTextView("\nСостав вашей семьи:")
            for ((key, value) in parentDemandList) {
                addTextToMainTextView("взрослый ${key} получает ${parentDemand[key]}")
            }
            addTextToMainTextView("\nКоличество детей: ${binding.etChildren.text.toString()}")
            peopleCount =
                binding.etChildren.text.toString().toInt() + binding.etOldCount.text.toString()
                    .toInt()
            addTextToMainTextView("\nВсего человек: ${peopleCount.toString()}")
            familyDemandByLivingWage = peopleCount * regionLivingWage
            addTextToMainTextView(
                "\nЕжемесячные затраты на жизнь по прожиточному минимуму для всей семьи составляю: ${
                    familyDemandByLivingWage
                }"
            )
            addTextToMainTextView("\nСтоимость аренды жилья: ${binding.etRentalHousing.text.toString()}")
            addTextToMainTextView("\nСтоимость коммунальных услуг: ${binding.etHouseService.text.toString()}")
            addTextToMainTextView("\nКоличество праздников: ${binding.etEvents.text.toString()}")
            addTextToMainTextView("\nСумма сбережений: ${binding.etSavings.text.toString()}")
            addTextToMainTextView("\nЗапланирована крупная покупка: ${binding.etMajorPurchase.text.toString()}")
            addTextToMainTextView("\nУ членов вашей семьи есть проблемы со здоровьем: ${binding.etHealthProblems.text.toString()}")
            addTextToMainTextView("\nУ вашей семьи есть страховка: ${binding.etInsurance.text.toString()}")
            addTextToMainTextView("\nСтоимость страховки: ${insuranceCost.toString()}")
            addTextToMainTextView("\nУ вашей семьи запланирован ребенок: ${binding.etBabyPlan.text.toString()}")
            addTextToMainTextView("\n\nРЕШЕНИЕ ПО ВАШЕЙ СЕМЬЕ:")

            binding.llMainContentContainer.removeAllViews()

            calculate()
        }
        setContentView(binding.root)
    }

    //функция добавления текста в главный лейбл чтобы выводить результат
    fun addTextToMainTextView(addableTextString: String) {
        binding.tvMainText.text = binding.tvMainText.text.toString() + "\n" + addableTextString
    }

    //функция проведения расчета
    fun calculate() {
        for ((key, value) in parentDemandList) {
            /*addTextToMainTextView("взрослый ${key} получает ${parentDemand[key]}")*/
            if (parentDemand[key] != null) {
                familyDemandCount += parentDemand[key]!!
            }
        }
        addTextToMainTextView("\nЕжемесячный доход вашей семьи: ${familyDemandCount}")

        //обязательные расходы
        var mandatoryExpenses = binding.etRentalHousing.text.toString().toInt()
        mandatoryExpenses += binding.etHouseService.text.toString().toInt()
        mandatoryExpenses += binding.etTransport.text.toString().toInt()
        mandatoryExpenses += familyDemandByLivingWage

        familyExpensesCount = mandatoryExpenses + (insuranceCost/12)
        addTextToMainTextView("\nЕжемесячные траты вашей семьи: ${familyExpensesCount}")

        addTextToMainTextView("\nЕжемесячные обязательные расходы: ${mandatoryExpenses}")

        //сообщения
        val budgetDeficit = "Дефицит бюджета!"
        val needAnotherDemand = "Семье рекомендуется найти дополнительные источники заработка."
        val needAnotherHouse = "Семье рекомендуется выбрать другое жилье для проживания."

        //пропорции бюджета
        var main = 50
        var optional = 30
        var savings = 20

        //1
        if (familyDemandCount < familyExpensesCount) {
            addTextToMainTextView("\n1. Сумма доходов семьи меньше суммы расходов")
            addTextToMainTextView(budgetDeficit)
            addTextToMainTextView(needAnotherDemand)
        }

        //2
        if (mandatoryExpenses > (familyDemandCount / 2)) {
            addTextToMainTextView("\n2. Сумма обязательных расходов семьи превышает 50% бюджета")
            addTextToMainTextView(needAnotherDemand)
        }

        //3
        if (binding.etRentalHousing.text.toString().toInt() > (familyDemandCount / 2)) {
            addTextToMainTextView("\n3. Стоимость оплаты аренды жилья превышает 50% бюджета")
            addTextToMainTextView(needAnotherHouse)
        }

        //4
        if (familyDemandByLivingWage > (familyDemandCount / 2)) {
            addTextToMainTextView("\n4. Стоимость суммарной потребительской корзины на семью превышает 50% бюджета")
            addTextToMainTextView(needAnotherDemand)
            optional = optional - 10
        }

        //5
        if (familyDemandCount < familyDemandByLivingWage) {
            addTextToMainTextView("\n5. Бюджет семьи менее стоимости суммарной потребительской корзины. Нищета.")
            addTextToMainTextView(needAnotherDemand)
        }

        //6
        if (binding.etTransport.text.toString().toInt() > (familyDemandCount / 2)) {
            addTextToMainTextView("\n6. Сумма транспортных расходов семьи превышает 50% бюджета")
            addTextToMainTextView(needAnotherDemand)
            addTextToMainTextView(needAnotherHouse)
        }

        //7
        if (binding.etHouseService.text.toString().toInt() > (familyDemandCount / 2)) {
            addTextToMainTextView("\n7. Сумма коммунальных расходов семьи превышает 50% бюджета")
            addTextToMainTextView(needAnotherDemand)
            addTextToMainTextView(needAnotherHouse)
        }

        //8
        if (familyDemandCount - familyExpensesCount - (familyDemandCount * optional / 100) < (familyDemandCount * .2)) {
            addTextToMainTextView("\n8. Для сбережений остается менее 20% бюджета")
            addTextToMainTextView(needAnotherDemand)
            optional = optional / 2
        }

        //9
        if (familyExpensesCount < familyDemandCount / 2) {
            addTextToMainTextView("\n9. Сумма обязательных расходов менее 50% бюджета")
            //увеличиваем размер необяз. и резервов
            var percent = (familyDemandCount / 2 - familyExpensesCount) / 4
            optional += percent
            savings += percent
        }

        //10
        if (familyDemandCount == familyExpensesCount) {
            addTextToMainTextView("\n10. Сумма расходов семьи равна 50% бюджета")
            main = 50
            optional = 30
            savings = 20
        }
        var savingsCount = binding.etSavings.text.toString()
        var savingsCountInt = 0
        if (savingsCount != "") {
            savingsCountInt = savingsCount.toInt()
        }

        //11
        if (savingsCountInt == 0 && optional >= 15 && savings < 20) {
            addTextToMainTextView("\n11. Сбережений нет, и необязательные расходы более 15% бюджета, в резерв откладывается менее 20% средств")
            addTextToMainTextView(needAnotherDemand)
            var difference = optional / 2
            optional = optional / 2
            savings += difference
        }

        var celebrationCount = binding.etEvents.text.toString().toInt()
        //12
        if (celebrationCount > 2 && optional < 5 && savings > 5) {
            addTextToMainTextView("\n12. Много праздников, необязательные расходы менее 5%, сбережения более 5%")
            addTextToMainTextView(needAnotherDemand)
            savings -= 5
            optional += 5
        }

        //13
        if (celebrationCount > 2 && optional < 5 && savings < 5) {
            addTextToMainTextView("\n13. Много праздников, необязательные расходы менее 5%, сбережения менее 5%")
            addTextToMainTextView(needAnotherDemand)
            addTextToMainTextView(budgetDeficit)
        }

        //14
        if (binding.etMajorPurchase.text.toString().toInt() >= 0 && optional > 15) {
            addTextToMainTextView("\n14. Планируется крупная покупка и необязательные расходы более 15%")
            var dif = optional / 2
            optional -= dif
            savings += dif
        }

        //15
        if (binding.etHealthProblems.text.toString() == "Да" && optional > 15 && savings < 20) {
            addTextToMainTextView("\n15. Имеются жалобы на здоровье и в резерве менее 20%, в необязательных расходах более 15%")
            var diff = 0
            if (optional - 5 < 0) {
                diff = optional
            } else {
                diff = 5
            }
            savings += diff
            optional -= diff
        }

        //16
        if (binding.etInsurance.text.toString() == "Нет" && optional <= 30 && (familyExpensesCount < familyDemandCount / 2)) {
            addTextToMainTextView("\n16. У семьи нет страховки. Основные расходы менее 50%. Необязательные расходы ${optional}% бюджета.")
            var diff = 0
            if (optional - 3 < 0) {
                diff = optional
            } else {
                diff = 3
            }
            savings += diff
            optional -= diff
        }

        //17
        if (binding.etBabyPlan.text.toString() == "Да" && (familyExpensesCount < familyDemandCount / 2) && optional >= 20) {
            addTextToMainTextView("\n17. Планируется ребенок и необязательные расходы более 20% (${optional}%)")
            addTextToMainTextView(needAnotherDemand)
            optional -= 10
            savings += 10
        }

        //заключение по бюджету
        var percent = (familyDemandCount - familyExpensesCount) / familyDemandCount * 100
        var different = 0
        if (percent < 0) {
            main = 100
            optional = 0
            savings = 0

        } else {
            main = 100-percent
            optional = percent * optional/100
            savings = percent * savings/100
        }

        addTextToMainTextView(
            "\n\n" +
                    "Ваш бюджет должен состять из:" +
                    "\n\nобязательные расходы: ${main}% бюджета или ${familyDemandCount * main / 100}" +
                    "\n\nнеобязательные расходы: ${optional}% бюджета или ${familyDemandCount * optional / 100}" +
                    "\n\nоткладывайте: ${savings}% бюджета или ${familyDemandCount * savings / 100}"
        )
    }
}