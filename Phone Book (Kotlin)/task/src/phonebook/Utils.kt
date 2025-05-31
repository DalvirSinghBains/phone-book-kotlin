package phonebook

import java.io.BufferedReader
import java.io.FileReader
import java.time.Instant
import java.time.Duration

class DataProcessor(private val moments: Pair<Instant, Instant>) {
    fun duration(): String {
        val duration = Duration.between(moments.first, moments.second)
        return "${duration.toMinutesPart()} min. " +
                "${duration.toSecondsPart()} sec. " +
                "${duration.toMillisPart()} ms."
    }

    fun maxAllowedSortingTime(): Long {
        val lower = moments.first.epochSecond
        val upper = moments.second.epochSecond
        val condition = upper + (10 * (upper - lower))
        return condition
    }
}

class PhoneBookInitialization {
        private val BASE_PATH = "C:\\Users\\dalvi\\Downloads\\phone-book-project"
        private val DB_PATH = "$BASE_PATH\\directory.txt"
        private val SEARCH_KEYS_PATH = "$BASE_PATH\\find.txt"
        val unsortedData = BufferedReader(FileReader(DB_PATH))
            .use { reader -> reader.readText()
                .trimEnd('\n').split('\n').toMutableList() }
        val keysToSearch = BufferedReader(FileReader(SEARCH_KEYS_PATH))
            .use { reader -> reader.readText().trimEnd('\n').split('\n') }
}