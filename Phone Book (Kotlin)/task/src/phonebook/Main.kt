package phonebook

import phonebook.sorted.BinarySearch
import phonebook.sorted.JumpSearch
import phonebook.unsorted.HashSearch
import phonebook.unsorted.LinearSearch

fun main() {
    val phoneBook = PhoneBookInitialization()
    val unsortedData = phoneBook.unsortedData
    val keysToSearch = phoneBook.keysToSearch

    val results = mutableListOf<SearchResult>()

    val linearSearchResult = LinearSearch(
        unsortedData = unsortedData.toMutableList(),
        keysToSearch = keysToSearch
    ).linearSearch().buildResult()

    val jumpSearchResult = JumpSearch(
        unsortedData = unsortedData.toMutableList(), keysToSearch = keysToSearch,
        thresholdSortTime = linearSearchResult.searchTime
    ).init().buildResult()

    val binarySearchResult = BinarySearch(
        unsortedData = unsortedData.toMutableList(),
        keysToSearch = keysToSearch
    ).init().buildResult()

    val hashSearchResult = HashSearch(
        unsortedData = unsortedData.toMutableList(),
        keysToSearch = keysToSearch
    ).init().buildResult()

    results.add(linearSearchResult)
    results.add(jumpSearchResult)
    results.add(binarySearchResult)
    results.add(hashSearchResult)

    for (result in results) {
        println(result)
    }
}



