package assertions

import offlineMusicLibrary.fileSystemOps.MusicFile
import org.junit.jupiter.api.Assertions.assertEquals

interface IMusicListAssertions {
    fun <T> assertListEqual(actual: List<T>, expected: List<T>) {
        for (i in actual.indices) {
            assertEquals(expected[i].toString(), actual[i].toString())
            //TODO  maybe find a more effecient way for this
        }
    }


}