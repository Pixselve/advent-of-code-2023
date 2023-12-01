import java.lang.Error
fun getReplacement(number: String): String {
    return when (number) {
        "one"  -> "1"
        "two"  -> "2"
        "three" -> "3"
        "four" -> "4"
        "five" -> "5"
        "six" -> "6"
        "seven" -> "7"
        "eight" -> "8"
        "nine" -> "9"
        else -> ""
    }
}
fun replaceDigitFromLeftToRight(line: String): Int {
    val onlyDigits = Regex("\\d").findAll(line)

    val result = Regex("one|two|three|four|five|six|seven|eight|nine").find(line)
        ?: return onlyDigits.first().value.toInt() * 10 + onlyDigits.last().value.toInt()

    val firstDigit = onlyDigits.firstOrNull();
    val lastDigit = onlyDigits.lastOrNull();
    val first = if (firstDigit != null && result.range.first >= firstDigit.range.first) {
        firstDigit.value.toInt()
    } else {
        getReplacement(result.value).toInt()
    }
    val resultFromTheEnd = Regex("one|two|three|four|five|six|seven|eight|nine".reversed()).find(line.reversed())

    if (lastDigit == null && resultFromTheEnd == null) {
        throw Error("lastDigit == null && resultFromTheEnd == null")
    }
    val second = if (lastDigit != null && ((line.length - 1) - resultFromTheEnd!!.range.first) <= lastDigit.range.first) {
        lastDigit.value.toInt()
    } else {
        getReplacement(resultFromTheEnd!!.value.reversed()).toInt()
    }
    return first * 10 + second
}

fun main() {
    fun part1(input: List<String>): Int {
         return input
             .asSequence()
             .map { line -> line.filter(Char::isDigit) }
             .map { el -> el.first() + "" + el.last() }
             .map(String::toInt)
             .reduce {acc, v -> acc + v }
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { line -> replaceDigitFromLeftToRight(line) }
            .reduce {acc, v -> acc + v }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()

}
