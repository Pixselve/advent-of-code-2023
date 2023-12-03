val directions = arrayOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1), Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))

fun isNumberIsAdjacentToASymbol(row: Int, range: IntRange, input: List<String>): Boolean {
    for (i in range) {
        for (direction in directions) {
            val adjacent = input.getOrNull(row + direction.first)?.getOrNull(i + direction.second) ?: '.'
            if (!(adjacent.isDigit() || adjacent == '.')) return true
        }
    }
    return false
}

fun isGearAdjacentTo2Numbers(row: Int, column: Int, input: List<String>): Int {
    val numbers = mutableSetOf<Pair<Int, IntRange>>()

    for (direction in directions) {
        val adjacent = input.getOrNull(row + direction.first)?.getOrNull(column + direction.second) ?: '.'
        if (adjacent.isDigit()) {
            numbers.add(Pair(row + direction.first, getFullNumberRange(input[row + direction.first], column + direction.second)))
        }
    }

    if (numbers.size == 2) {
        return numbers.fold(1) { acc, pair -> acc * input[pair.first].substring(pair.second).toInt() }
    }
    return 0
}

fun getFullNumberRange(row: String, pointer: Int): IntRange {
    var first = pointer
    var last = pointer

    while (first > 0) {
        if (row[first - 1].isDigit()) {
            first--
        } else {
            break
        }
    }

    while (last < row.length - 1) {
        if (row[last + 1].isDigit()) {
            last++
        } else {
            break
        }
    }

    return IntRange(first, last)
}


val numberRegex = Regex("\\d+")
val gearRegex = Regex("\\*")
fun main() {
    fun part1(input: List<String>): Int = input
        .flatMapIndexed {index: Int, s: String -> numberRegex.findAll(s).map { Pair(index, it) } }
        .filter { isNumberIsAdjacentToASymbol(it.first, it.second.range, input) }
        .sumOf { it.second.value.toInt() }

    fun part2(input: List<String>): Int = input
        .flatMapIndexed { index: Int, s: String -> gearRegex.findAll(s).map { Pair(index, it.range.first) } }
        .sumOf { isGearAdjacentTo2Numbers(it.first, it.second, input) }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    part1(testInput).println()
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
