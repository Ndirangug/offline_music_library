package assertions

import org.junit.jupiter.api.Assertions.assertEquals

interface IMusicListAssertions {
    fun <T> assertListEqual(expected: List<T>, actual: List<T>) {
        for (i in actual.indices) {
            assertEquals(expected[i].toString(), actual[i].toString())
            //TODO  maybe find a more effecient way for this

        }
    }


}