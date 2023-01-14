package converter

enum class Distances(val group: String, val briefly: String, val full: String, val many: String, val uni: Double, var dop: String = "") {
    METERS("Distances","m","meter","meters",1.0),
    KILOMETERS("Distances","km","kilometer","kilometers",1000.0),
    CENTIMETERS("Distances","cm","centimeter","centimeters",0.01),
    MILLIMETERS("Distances","mm","millimeter","millimeters",0.001),
    MILES("Distances","mi","mile","miles",1609.35),
    YARDS("Distances","yd","yard","yards",0.9144),
    FEET("Distances","ft","foot","feet",0.3048),
    INCHES("Distances","in","inch","inches",0.0254),

    GRAMS("Weight","g","gram","grams",1.0),
    KILOGRAMS("Weight","kg","kilogram","kilograms",1000.0),
    MILLIGRAMS("Weight","mg","milligram","milligrams",0.001),
    POUNDS("Weight","lb","pound","pounds",453.592),
    OUNCES("Weight","oz","ounce","ounces",28.3495),

    CELSIUS("Degree","dc","degree Celsius","degrees Celsius", 1.0, "c"),
    FAHRENHEIT("Degree","df","degree Fahrenheit","degrees Fahrenheit", 1.0, "f"),
    KELVINS("Degree","k","kelvin","kelvins", 1.0),

    NULL("1","1","1","1",1.0),
}

fun dis (units: String): Distances {
    for (distances in Distances.values()) {
        if (units.toLowerCase() == distances.briefly) return distances
    }
    for (distances in Distances.values()) {
        if (units.toLowerCase() == distances.full) return distances
    }
    for (distances in Distances.values()) {
        if (units.toLowerCase() == distances.many) return distances
    }
    for (distances in Distances.values()) {
        if (units.toLowerCase() == distances.dop) return distances
    }
    for (enum in Distances.values()) {
        if (units.toUpperCase() == enum.name) return enum
    }
    return Distances.NULL
}

fun main() {
    val a = 0
    while (a == 0) {
        print("Enter what you want to convert (or exit): ")
        val s = readln()
        if (s == "exit") break
        try {
            val str = s.toLowerCase().split(" degree ", " degrees ", " ")
            val number = str[0].toDouble()
            val initialUnit = dis(str[1])
            val finalUnit = dis(str[3])
            when {
                initialUnit == Distances.NULL && finalUnit == Distances.NULL -> {
                    println("Conversion from ??? to ??? is impossible")
                    println()
                }

                initialUnit == Distances.NULL && finalUnit != Distances.NULL -> {
                    println("Conversion from ??? to ${finalUnit.many} is impossible")
                    println()
                }

                initialUnit != Distances.NULL && finalUnit == Distances.NULL -> {
                    println("Conversion from ${initialUnit.many} to ??? is impossible")
                    println()
                }

                initialUnit.group != finalUnit.group -> {
                    println("Conversion from ${initialUnit.many} to ${finalUnit.many} is impossible")
                    println()
                }

                number < 0 && initialUnit.group == "Weight" -> {
                    println("Weight shouldn't be negative")
                    println()
                }

                number < 0 && initialUnit.group == "Distances" -> {
                    println("Length shouldn't be negative")
                    println()
                }

                else -> metric(number, initialUnit, finalUnit)
            }
        } catch (e: Exception) {
            println("Parse error")
            println()
        }
    }
}

fun metric(_number: Double, _unit1: Distances, _unit2: Distances) {
    var res = _number
    if (_unit1.group == "Degree") {
        when {
            _unit1.name == "CELSIUS" && _unit2.name == "FAHRENHEIT" -> res = _number * 9 / 5 + 32
            _unit2.name == "CELSIUS" && _unit1.name == "FAHRENHEIT" -> res = (_number - 32) * 5 / 9
            _unit1.name == "CELSIUS" && _unit2.name == "KELVINS" -> res = _number + 273.15
            _unit2.name == "CELSIUS" && _unit1.name == "KELVINS" -> res = _number - 273.15
            _unit1.name == "FAHRENHEIT" && _unit2.name == "KELVINS" -> res = (_number + 459.67) * 5 / 9
            _unit2.name == "FAHRENHEIT" && _unit1.name == "KELVINS" -> res = _number * 9 / 5 - 459.67
        }
    } else {
        res = _number * _unit1.uni / _unit2.uni
    }
    when {
        _number == 1.0 && res == 1.0 -> println("$_number ${_unit1.full} is $res ${_unit2.full}")
        _number == 1.0 -> println("$_number ${_unit1.full} is $res ${_unit2.many}")
        res == 1.0 -> println("$_number ${_unit1.many} is $res ${_unit2.full}")
        else -> println("$_number ${_unit1.many} is $res ${_unit2.many}")
    }
    println()
}
