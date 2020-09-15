package ru.skillbranch.devintensive.utils

import android.content.Context
import java.util.*
import kotlin.math.roundToInt

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName?.trim().equals("") || fullName.equals(null))
            return null to null;

        val parts: List<String>? = fullName?.trim()?.split(" ");

        val firstName = parts?.getOrNull(0);
        val lastName = parts?.getOrNull(1);

        return firstName to lastName;
    }

    fun toInitials(firstName:String?, lastName:String?): String? {
        val firstLetter = if (firstName?.trim().isNullOrEmpty()) null else firstName?.trim()?.first()?.toUpperCase();
        val secondLetter = if (lastName?.trim().isNullOrEmpty()) null else lastName?.trim()?.first()?.toUpperCase();

        return when {
            firstLetter == null && secondLetter == null -> null
            firstLetter == null -> "$secondLetter"
            secondLetter == null -> "$firstLetter"
            else -> "$firstLetter$secondLetter"
        };
    }

    fun transliteration(payload:String, divider:String = " "): String {
        val dictionary = mapOf(
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d", 'е' to "e", 'ё' to "e",
            'ж' to "zh", 'з' to "z", 'и' to "i", 'й' to "i", 'к' to "k", 'л' to "l", 'м' to "m",
            'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t", 'у' to "u",
            'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch", 'ш' to "sh", 'щ' to "sh'", 'ъ' to "",
            'ы' to "i", 'ь' to "", 'э' to "e", 'ю' to "yu", 'я' to "ya"
        );

        var result = ""

        payload.forEach { char ->
            var isUppercase = char.isUpperCase()
            var key = char.toLowerCase()
            var simbol = if (char.isWhitespace()) divider else dictionary.getOrDefault(key,char.toString())
            if (isUppercase) {
                result += if (simbol.length in (0..1)) (simbol.toUpperCase())
                else (simbol.get(0).toUpperCase().toString() + simbol.drop(1))
            } else {
                result += simbol
            }
        }

        return result
    }


    fun convertDpToPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

}