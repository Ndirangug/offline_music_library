package assertions

import offlineMusicLibrary.fileSystemOps.MusicFile
import org.junit.jupiter.api.Assertions.assertEquals

interface IMusicListAssertions {
    fun assertListEqual(actual: MutableList<MusicFile>, expected: MutableList<MusicFile>) {
        for (i in actual.indices) {
            assertEquals(expected[i].toString(), actual[i].toString())
            //TODO  maybe find a more effecient way for this
        }
    }
}