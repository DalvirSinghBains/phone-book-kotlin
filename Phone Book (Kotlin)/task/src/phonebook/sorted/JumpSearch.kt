package phonebook.sorted

import phonebook.DataProcessor
import phonebook.JumpSearchResult
import phonebook.SortedSearchResult
import phonebook.unsorted.LinearSearch
import java.time.Instant
import kotlin.math.min
import kotlin.math.sqrt

class JumpSearch(
    private val unsortedData: MutableList<String>,
    private val keysToSearch: List<String>,
    private val thresholdSortTime: Pair<Instant, Instant>
) {
    private var keysFound: Int = 0
    private val totalKeys: Int = keysToSearch.size
    private lateinit var startTime: Instant
    private lateinit var endTime: Instant
    private lateinit var sortTime: Pair<Instant, Instant>
    private lateinit var searchTime: Pair<Instant, Instant>
    private lateinit var totalTime: Pair<Instant, Instant>
    private var isSortingCompleted: Boolean = true

    fun init(): JumpSearch {
        startTime = Instant.now()
        val sortedList = bubbleSort(list = unsortedData, thresholdSortTime)
        if (sortedList.isNotEmpty()) {
            val searchStarted = Instant.now()
            val foundIndexList = jumpSearch(list = sortedList, keys = keysToSearch)
            val searchEnded = Instant.now()
            keysFound = foundIndexList.count { it != -1 }
            searchTime = Pair(searchStarted, searchEnded)
        } else {
            val result = LinearSearch(unsortedData, keysToSearch).linearSearch().buildResult()
            keysFound = result.keysFound
            searchTime = result.searchTime
        }
        endTime = Instant.now()
        totalTime = Pair(startTime, endTime)
        return this
    }

    private fun bubbleSort(
        list: MutableList<String>,
        threshold: Pair<Instant, Instant>
    ): MutableList<String> {
        val condition = DataProcessor(threshold).maxAllowedSortingTime()

        val sortStarted = Instant.now()
        for (i in 0..<list.size - 1) {
            if (Instant.now().epochSecond > condition) {
                isSortingCompleted = false
                sortTime = Pair(sortStarted, Instant.now())
                return mutableListOf()
            }
            for (j in 0..<list.size - i - 1) {
                if (list[j].substringAfter(' ') > list[j + 1].substringAfter(' ')) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        val sortEnded = Instant.now()
        sortTime = Pair(sortStarted, sortEnded)
        return list
    }

    private fun jumpSearch(list: MutableList<String>, keys: List<String>): MutableList<Int> {
        val foundIndexList = mutableListOf<Int>()
        for (key in keys) {
            val index = jSearch(list = list, key = key)
            foundIndexList.add(index)
        }
        return foundIndexList
    }

    private fun jSearch(list: MutableList<String>, key: String): Int {
        val n = list.size
        var step = sqrt(n.toDouble()).toInt()
        var prev = 0
        while (list[min(step, n) - 1].substringAfter(' ') < key) {
            prev = step
            step += sqrt(n.toDouble()).toInt()
            if (prev >= n) {
                return -1
            }
        }
        while (list[prev].substringAfter(' ') < key) {
            prev++
            if (prev == min(step, n)) {
                return -1
            }
        }
        return if (list[prev].substringAfter(' ') == key) prev else -1
    }

    fun buildResult(): SortedSearchResult {
        return JumpSearchResult(
            keysFound = this.keysFound, totalKeys = this.totalKeys,
            searchTime = this.searchTime, totalTime = this.totalTime, sortTime = this.sortTime,
            isSortingCompleted = this.isSortingCompleted
        )
    }
}