import kotlin.io.path.Path
import kotlin.io.path.readText


data class Node(val key: LongRange, val destinationRange: LongRange, var left: Node? = null, var right: Node? = null) {
    fun insert(value: LongRange, destinationRange: LongRange) {
        if (value.first > key.first) {
            if (right == null) right = Node(value, destinationRange) else right!!.insert(value, destinationRange)
        } else {
            if (left == null) left = Node(value, destinationRange) else left!!.insert(value, destinationRange)
        }
    }

    fun find(value: Long): Long? {
        if (key.contains(value)) {
            val distance = value - key.first
            return destinationRange.first + distance
        }
        return if (value > key.first) {
            if (right == null) null else right!!.find(value)
        } else {
            if (left == null) null else left!!.find(value)
        }
    }
}

data class Map(val tree: Node)

data class MapValue(val destinationRange: LongRange, val sourceRange: LongRange)

fun parseMap(raw: String): Node? {
    var node: Node? = null

    raw
        .lines()
        .drop(1)
        .filter { it.isNotEmpty() }
        .map {
        val (destination, source, range) = it.split(" ").map { s -> s.toLong() }
        MapValue(destination.rangeTo(destination + range - 1), source.rangeTo(source + range - 1))
    }.forEach {
        if (node == null) {
            node = Node(it.sourceRange, it.destinationRange)
        } else {
            node!!.insert(it.sourceRange, it.destinationRange)
        }
    }
    return node
}

fun main() {
    fun part1(filePath: String): Long {
        val mapsRaw = Path(filePath).readText().split("\n\n")
        val rest = mapsRaw.drop(1)
        var seedsIds = mapsRaw.first().drop(7).split(" ").map { it.toLong() }
        val maps = rest.filter { it.isNotEmpty() }.map { parseMap(it) }

        maps.forEach {node ->
            seedsIds = seedsIds.map {
                node?.find(it) ?: it
            }
        }

        return seedsIds.min()
    }





    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    check(part1("src/Day05_test.txt") == 35L)
//    check(part2(testInput) == 0)
//    val input = readInput("Day05")
    part1("src/Day05.txt").println()
//    part2(input).println()
}
