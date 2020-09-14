package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String? {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND):Date {
    var time = this.time;

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value:Int): String {
        return "$value ${getPluralForm(value, this)}";
    }
}

enum class Plurals(private val second: String, private val minute: String, private val hour: String, private val day: String){
    ONE("секунду", "минуту", "час", "день"),
    FEW("секунды", "минуты", "часа", "дня"),
    MANY("секунд","минут", "часов", "дней");

    fun get(unit: TimeUnits): String {
        return when(unit){
            TimeUnits.SECOND -> second
            TimeUnits.MINUTE -> minute
            TimeUnits.HOUR -> hour
            TimeUnits.DAY -> day
        }
    }
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val time = this.time;

    val diff = abs(date.time - time);
    val diffSign = date.time - time > 0;

    return when {
        diff <= SECOND -> "только что"
        diff > SECOND && diff <= SECOND * 45 -> getTenseForm("несколько секунд", diffSign)
        diff > SECOND * 45 && diff <= SECOND * 75 -> getTenseForm("минуту", diffSign)
        diff > SECOND * 75 && diff <= MINUTE * 45 -> getTenseForm(TimeUnits.MINUTE.plural((diff / MINUTE).toInt()), diffSign)
        diff > MINUTE * 45 && diff <= MINUTE * 75 -> getTenseForm("час", diffSign)
        diff > MINUTE * 75 && diff <= 22 * HOUR -> getTenseForm(TimeUnits.HOUR.plural((diff / HOUR).toInt()), diffSign)
        diff > 22 * HOUR && diff <= 26 * HOUR ->  getTenseForm("день", diffSign)
        diff > 26 * HOUR && diff <= 360 * DAY -> getTenseForm(TimeUnits.DAY.plural((diff / DAY).toInt()), diffSign)
        else -> if(diffSign) "более года назад" else "более чем через год"
    }
}

fun getTenseForm(interval: String, diffSign: Boolean):String {
    val prefix = if (diffSign) "" else "через"
    val postfix = if (diffSign) "назад" else ""
    return "$prefix $interval $postfix".trim()
}

fun getPluralForm(amount: Int, units: TimeUnits): String {
    return when(val posAmount = abs(amount) % 100){
        1 -> Plurals.ONE.get(units)
        in 2..4 -> Plurals.FEW.get(units)
        0, in 5..19 -> Plurals.MANY.get(units)
        else -> getPluralForm(posAmount % 10, units)
    }
}

