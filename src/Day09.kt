fun process(pyramid: MutableList<MutableList<Int>>): Int {
    while (!pyramid.last().all { it == 0 }) {
        pyramid.add(pyramid.last().windowed(2) { (a, b) -> b - a }.toMutableList())
    }
    pyramid.last().add(0)
    pyramid.reversed().windowed(2).forEach { (a, b) -> b.add(b.last() + a.last()) }
    return pyramid.first().last()
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { mutableListOf(it.split(" ").map(String::toInt).toMutableList()) }
            .sumOf { process(it) }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { mutableListOf(it.split(" ").map(String::toInt).reversed().toMutableList()) }
            .sumOf { process(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
