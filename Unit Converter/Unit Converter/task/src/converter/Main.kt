package converter

import java.util.*
import kotlin.NoSuchElementException

fun main() {
    Converter
}

object Converter {

    init {
        prompt()
    }

    private fun prompt() {
        print("Enter what you want to convert (or exit): ")
        val data = Scanner(System.`in`).nextLine().split(" ")
        if (data[0] == "exit") return

        convert2final(data.first().toDouble(), data[1], data.last())
        prompt()
    }

    private fun convert2final(value:Double, firstMeasure: String,  finalMeasure: String) {
        val from = Units.getTypeBySynonym(firstMeasure)
        val destination = Units.getTypeBySynonym(finalMeasure)
        if (from == null) {
            if (destination == null) {
                println("Conversion from ??? to ??? is impossible")
            } else {
                println("Conversion from ??? to ${destination.plural} is impossible")
            }
        } else if (destination == null) {
            println("Conversion from ${from.plural} to ??? is impossible")
        } else {
            var result: Double = 0.0
            if (from.type == destination.type) {
                result = value * from.conversion / destination.conversion
                println("$value ${if (value != 1.0) from.plural else from.name.toLowerCase()} is $result ${if (result != 1.0) destination.plural else destination.name.toLowerCase()}")
            } else {
                println("Conversion from ${from.plural} to ${destination.plural} is impossible")
            }
        }

    }
}

enum class UnitType {
    WEIGHT, LENGTH
}

enum class Units(val synonyms: List<String>, val type: UnitType, val plural: String, val conversion: Double) {
    METER(listOf("m", "meter", "meters"), UnitType.LENGTH, "meters", 1.0),
    KILOMETER(listOf("km", "kilometer", "kilometers"), UnitType.LENGTH, "kilometers", 1000.0),
    CENTIMETER(listOf("cm", "centimeter", "centimeters"), UnitType.LENGTH, "centimeters", 0.01),
    MILLIMETER(listOf("mm", "millimeter", "millimeters"), UnitType.LENGTH, "millimeters", 0.001),
    MILE(listOf("mi", "mile", "miles"), UnitType.LENGTH, "miles", 1609.35),
    YARD(listOf("yd", "yard", "yards"), UnitType.LENGTH, "yards", 0.9144),
    FOOT(listOf("ft", "foot", "feet"), UnitType.LENGTH, "feet", 0.3048),
    INCH(listOf("in", "inch", "inches"), UnitType.LENGTH, "inches", 0.0254),

    GRAM(listOf("g", "gram", "grams"), UnitType.WEIGHT, "grams", 1.0),
    KILOGRAM(listOf("kg", "kilogram", "kilograms"), UnitType.WEIGHT, "kilograms", 1000.0),
    MILLIGRAM(listOf("mg", "milligram", "milligrams"), UnitType.WEIGHT, "milligrams", 0.001),
    POUND(listOf("lb", "pound", "pounds"), UnitType.WEIGHT, "pounds", 453.592),
    OUNCE(listOf("oz", "ounce", "ounces"), UnitType.WEIGHT, "ounces", 28.3495);

    companion object {
        fun getTypeBySynonym(name: String): Units? {
            return try {
                values().first { name.toLowerCase() in it.synonyms }
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }


}