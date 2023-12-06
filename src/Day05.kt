import kotlin.io.path.Path
import kotlin.io.path.readText
data class MapValue(val destinationRange: LongRange, val sourceRange: LongRange) {
    fun sourceToDest(source: Long): Long {
        if (!sourceRange.contains(source)) error("$sourceRange does not contains $source")
        return destinationRange.first + (source - sourceRange.first)
    }
}

fun parseMap(raw: String): List<MapValue> {
    return raw
        .lines()
        .drop(1)
        .filter { it.isNotEmpty() }
        .map {s ->
            val (destination, source, range) = s.split(" ").map { it.toLong() }
            MapValue(destination..<destination + range, source..<source + range)
        }.sortedBy { it.sourceRange.first }
}

fun getDestinationFromSource(map: List<MapValue>, source: Long): Long {
    map.find { it.sourceRange.contains(source) }.let {
        if (it == null) return source
        return it.sourceToDest(source)
    }
}
fun getDestinationFromSource(map: List<MapValue>, source: LongRange): List<LongRange> {
    val result = mutableListOf<LongRange>()
    var rest = source

    for (it in map) {
        // fully contained
        if (it.sourceRange.contains(rest.first) && it.sourceRange.contains(rest.last)) {
            result.add(it.sourceToDest(rest.first)..it.sourceToDest(rest.last))
            rest = LongRange.EMPTY
            break
        } else if (rest.last < it.sourceRange.first) {
            break
        } else if (it.sourceRange.contains(rest.last) && !it.sourceRange.contains(rest.first)) {
            result.add(rest.first..<it.sourceRange.first)
            result.add(it.sourceToDest(it.sourceRange.first)..it.sourceToDest(rest.last))
            rest = LongRange.EMPTY
            break
        } else if (it.sourceRange.contains(rest.first) && !it.sourceRange.contains(rest.last)) {
            result.add(it.sourceToDest(rest.first)..it.sourceToDest(it.sourceRange.last))
            rest = it.sourceRange.last + 1 .. rest.last
        } else if (rest.first < it.sourceRange.first && rest.last > it.sourceRange.last) {
            result.add(rest.first..<it.sourceRange.first)
            result.add(it.sourceToDest(it.sourceRange.first)..it.sourceToDest(it.sourceRange.last))
            rest = it.sourceRange.last + 1 .. rest.last
        }
    }
    if (!rest.isEmpty()) result.add(rest)
    return result
}

fun main() {
    fun part1(filePath: String): Long {
        val mapsRaw = Path(filePath).readText().split("\n\n")
        val rest = mapsRaw.drop(1)
        var seedsIds = mapsRaw.first().drop(7).split(" ").map { it.toLong() }
        val maps = rest.map { parseMap(it) }

        maps.forEach {map ->
            seedsIds = seedsIds.map {
                getDestinationFromSource(map, it)
            }
        }

        return seedsIds.min()
    }

    fun part2(filePath: String): Long {
        val mapsRaw = Path(filePath).readText().split("\n\n")
        val rest = mapsRaw.drop(1)
        var seedsIds = mapsRaw.first().drop(7).split(" ").map { it.toLong() }.chunked(2).map {
                (first, second) -> first..<second + first
        }

        val maps = rest.map { parseMap(it) }

        maps.forEach {map ->
            seedsIds = seedsIds.flatMap {
                getDestinationFromSource(map, it)
            }
        }
        return seedsIds.minOf { longRange: LongRange -> longRange.first }
    }

    // test if implementation meets criteria from the description, like:
    check(part1("src/Day05_test.txt") == 35L)
    part2("src/Day05_test.txt").println()
    check(part2("src/Day05_test.txt") == 46L)
    part1("src/Day05.txt").println()
    part2("src/Day05.txt").println()
}
