package phonebook

import java.time.Instant

open class SearchResult(
    open val message: String = "Start searching (linear search)...",
    open val keysFound: Int,
    open val totalKeys: Int,
    open val searchTime: Pair<Instant, Instant>
) {

    override fun toString(): String {
        return """
            $message
            Found $keysFound / $totalKeys entries. Time taken: ${DataProcessor(searchTime).duration()}
        """.trimIndent()
    }
}

open class SortedSearchResult(
    override val message: String = "Start searching after sorting...",
    keysFound: Int,
    totalKeys: Int,
    searchTime: Pair<Instant, Instant>,
    open val totalTime: Pair<Instant, Instant>,
    open val sortTime: Pair<Instant, Instant>,
    open val isSortingCompleted: Boolean = true
) : SearchResult(message, keysFound, totalKeys, searchTime) {
    private fun sortTimeString(): String {
        return if (isSortingCompleted) {
            DataProcessor(sortTime).duration()
        } else {
            DataProcessor(sortTime).duration() + " - STOPPED, moved to linear search"
        }
    }

    override fun toString(): String {
        return """
            $message
            Found $keysFound / $totalKeys entries. Time taken: ${DataProcessor(totalTime).duration()}
            Sorting time: ${sortTimeString()}
            Searching time: ${DataProcessor(searchTime).duration()}
        """.trimIndent()
    }
}

class JumpSearchResult(
    override val message: String = "Start searching (bubble sort + jump search)...",
    keysFound: Int,
    totalKeys: Int,
    searchTime: Pair<Instant, Instant>,
    totalTime: Pair<Instant, Instant>,
    sortTime: Pair<Instant, Instant>,
    isSortingCompleted: Boolean = true
) : SortedSearchResult(message, keysFound, totalKeys, searchTime, totalTime, sortTime, isSortingCompleted)

class BinarySearchResult(
    override val message: String = "Start searching (quick sort + binary search)...",
    keysFound: Int,
    totalKeys: Int,
    searchTime: Pair<Instant, Instant>,
    totalTime: Pair<Instant, Instant>,
    sortTime: Pair<Instant, Instant>,
    isSortingCompleted: Boolean = true
) : SortedSearchResult(message, keysFound, totalKeys, searchTime, totalTime, sortTime, isSortingCompleted)

class HashSearchResult(
    override val message: String = "Start searching (hash table)...",
    keysFound: Int,
    totalKeys: Int,
    searchTime: Pair<Instant, Instant>,
    private val totalTime: Pair<Instant, Instant>,
    private val creationTime: Pair<Instant, Instant>
) : SearchResult(message, keysFound, totalKeys, searchTime) {

    override fun toString(): String {
        return """
            $message
            Found $keysFound / $totalKeys entries. Time taken: ${DataProcessor(totalTime).duration()}
            Creating time: ${DataProcessor(creationTime).duration()}
            Searching time: ${DataProcessor(searchTime).duration()}
        """.trimIndent()
    }
}