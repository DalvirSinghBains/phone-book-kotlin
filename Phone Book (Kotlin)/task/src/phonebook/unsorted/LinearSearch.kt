package phonebook.unsorted

import phonebook.SearchResult
import java.time.Instant

class LinearSearch(
    private val unsortedData: MutableList<String>,
    private val keysToSearch: List<String>
) {
    private var keysFound: Int = 0
    private val totalKeys: Int = keysToSearch.size
    private lateinit var startTime: Instant
    private lateinit var endTime: Instant
    private lateinit var searchTime: Pair<Instant, Instant>

    fun linearSearch(): LinearSearch {
        startTime = Instant.now()
        for (key in keysToSearch) {
            for (data in unsortedData) {
                if (data.contains(key)) keysFound++
            }
        }
        endTime = Instant.now()
        searchTime = Pair(startTime, endTime)
        return this
    }

    fun buildResult(): SearchResult {
        return SearchResult(
            keysFound = this.keysFound, totalKeys = this.totalKeys,
            searchTime = this.searchTime
        )
    }
}