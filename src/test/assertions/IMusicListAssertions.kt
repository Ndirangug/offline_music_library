package assertions

import org.junit.jupiter.api.Assertions.assertEquals

interface IMusicListAssertions {
    fun <T> assertListEqual(expected: List<T>, actual: List<T>) {
        for (i in actual.indices) {
            assertEquals(expected[i].toString(), actual[i].toString())
            //TODO  maybe find a more effecient way for this

        }
    }

    fun <T> assertMapEqual(expected: Map<String, T>, actual: Map<String, T>) {

        assertEquals(expected.toString(), actual.toString())
//        for (i in actual) {
//            assertEquals(expected[i.key], i.value)
//
//            //TODO  maybe find a more effecient way for this
//
//        }
    }

}