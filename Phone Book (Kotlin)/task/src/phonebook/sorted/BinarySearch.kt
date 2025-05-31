package phonebook.sorted

import phonebook.BinarySearchResult
import phonebook.SortedSearchResult
import java.time.Instant

class BinarySearch(
    private val unsortedData: MutableList<String>,
    private val keysToSearch: List<String>
) {
    private var keysFound: Int = 0
    private val totalKeys: Int = keysToSearch.size
    private lateinit var startTime: Instant
    private lateinit var endTime: Instant
    private lateinit var sortTime: Pair<Instant, Instant>
    private lateinit var searchTime: Pair<Instant, Instant>
    private lateinit var totalTime: Pair<Instant, Instant>
    private var isSortingCompleted: Boolean = true

    fun init(): BinarySearch {
        startTime = Instant.now()
        // sorting starts using quick sort
        val sortStarted = Instant.now()
        val sortedList = quickSortLomuto(list = unsortedData)
        val sortEnded = Instant.now()
        sortTime = Pair(sortStarted, sortEnded)
        // searching starts using binary search
        val searchStarted = Instant.now()
        val foundIndexList = binarySearch(list = sortedList, keys = keysToSearch)
        val searchEnded = Instant.now()

        keysFound = foundIndexList.count { it != -1 }
        searchTime = Pair(searchStarted, searchEnded)
        endTime = Instant.now()
        totalTime = Pair(startTime, endTime)
        return this
    }

    private fun quickSortLomuto(
        list: MutableList<String>,
        low: Int = 0,
        high: Int = list.size - 1
    ): MutableList<String> {
        if (low < high) {
            val pi = partitionLomuto(list, low, high)
            quickSortLomuto(list, low, pi - 1)
            quickSortLomuto(list, pi + 1, high)
        }
        return list
    }

    private fun partitionLomuto(
        list: MutableList<String>,
        low: Int, high: Int
    ): Int {
        val pivot = list[high].substringAfter(' ')
        var i = (low - 1)
        for (j in low until high) {
            if (list[j].substringAfter(' ') <= pivot) {
                i++
                val temp = list[i]
                list[i] = list[j]
                list[j] = temp
            }
        }
        val temp = list[i + 1]
        list[i + 1] = list[high]
        list[high] = temp
        return i + 1
    }

    private fun binarySearch(list: MutableList<String>, keys: List<String>): MutableList<Int> {
        val foundIndexList = mutableListOf<Int>()
        for (key in keys) {
            val index = bSearch(list = list, key = key)
            foundIndexList.add(index)
        }
        return foundIndexList
    }

    private fun bSearch(list: MutableList<String>, key: String): Int {
        var left = 0
        var right = list.size - 1
        while (left <= right) {
            val mid = left + (right - left) / 2
            when {
                (list[mid].substringAfter(' ') == key) -> return mid
                (list[mid].substringAfter(' ') < key) -> left = mid + 1
                else -> right = mid - 1
            }
        }
        return -1
    }

    fun buildResult(): SortedSearchResult {
        return BinarySearchResult(
            keysFound = this.keysFound, totalKeys = this.totalKeys,
            searchTime = this.searchTime, totalTime = this.totalTime, sortTime = this.sortTime,
            isSortingCompleted = this.isSortingCompleted
        )
    }
}