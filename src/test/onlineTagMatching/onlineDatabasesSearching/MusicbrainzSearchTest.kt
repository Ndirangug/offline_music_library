package onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching.MusicbrainzSearch
import org.junit.jupiter.api.Test

import java.nio.file.Paths
import java.time.Year

internal class MusicbrainzSearchTest {

    @Test
    fun attemptRetrieveMetaTags() {
        val musicFileToBeUpdated =  MusicFile(
            filePath = Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath(),
            title = "",
            album = "",
            albumArtist = "",
            contributingArtists = listOf<String>(),
            genre = "",
            year = Year.parse("0000"),
            trackNumber = 0,
            trackLength = "",
            numberOfEmptyFields = 8
        )

        val expected = MusicFile(
            filePath = Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath(),
            title = "Endless Alleluia",
            album = "Victory",
            albumArtist = "Bethel Music",
            contributingArtists = listOf<String>("Bethel Music","Cory Asbury"),
            genre = "",
            year = Year.parse("2019"),
            trackNumber = 14,
            trackLength = "",
            numberOfEmptyFields = 2
        )
        val actual = MusicbrainzSearch.attemptRetrieveMetaTags(musicFileToBeUpdated)

    }
}