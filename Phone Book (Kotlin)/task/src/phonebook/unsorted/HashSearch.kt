package phonebook.unsorted

import phonebook.HashSearchResult
import phonebook.SearchResult
import java.time.Instant

class HashSearch(
    private val unsortedData: MutableList<String>,
    private val keysToSearch: List<String>
) {
    private var keysFound: Int = 0
    private val totalKeys: Int = keysToSearch.size
    private lateinit var startTime: Instant
    private lateinit var endTime: Instant
    private lateinit var searchTime: Pair<Instant, Instant>
    private lateinit var totalTime: Pair<Instant, Instant>
    private lateinit var creationTime: Pair<Instant, Instant>
    private val map = hashMapOf<String, MutableList<String>>()

    fun init(): HashSearch {
        startTime = Instant.now()
        createMap()
        searchMap()
        endTime = Instant.now()
        totalTime = Pair(startTime, endTime)
        return this
    }

    // creates a hashmap from person names to their phone numbers
    // phones of persons having same first and last names will be preserved in list
    private fun createMap() {
        val creationStart = Instant.now()
        for (item in unsortedData) {
            map.merge(
                item.substringAfter(' '),
                mutableListOf(item.substringBefore(' '))
            ) { oldValue, newValue ->
                oldValue.addAll(newValue)
                return@merge oldValue
            }
        }
        val creationEnd = Instant.now()
        creationTime = Pair(creationStart, creationEnd)
    }

    private fun searchMap() {
        val searchStarted = Instant.now()
        for (key in keysToSearch) {
            if (map[key] != null && map[key]!!.isNotEmpty()) keysFound++
        }
        val searchEnded = Instant.now()
        searchTime = Pair(searchStarted, searchEnded)
    }

    fun buildResult(): SearchResult {
        return HashSearchResult(
            keysFound = this.keysFound, totalKeys = this.totalKeys,
            searchTime = this.searchTime, totalTime = this.totalTime,
            creationTime = this.creationTime
        )
    }
}