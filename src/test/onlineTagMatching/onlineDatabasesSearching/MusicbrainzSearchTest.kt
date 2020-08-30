package onlineTagMatching.onlineDatabasesSearching

import annotations.LearningTest
import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching.MusicbrainzSearch
import org.junit.jupiter.api.Test

import java.nio.file.Paths
import java.time.Year
import kotlin.test.assertEquals

import org.musicbrainz.controller.Recording
import org.musicbrainz.model.searchresult.RecordingResultWs2

internal class MusicbrainzSearchTest {

    @Test
    fun attemptRetrieveMetaTags() {
        val musicFileToBeUpdated =  MusicFile(
            filePath = Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath(),
            //filePath = Paths.get("test_assets/(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3").toAbsolutePath(),
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
            trackNumber = 0, //TODO place correctt value here
            trackLength = "323",
            numberOfEmptyFields = 0 //TODO place correctt value here
        )
        val actual = MusicbrainzSearch.attemptRetrieveMetaTags(musicFileToBeUpdated)

        assertEquals(expected, actual)
    }

    enum class MusicBrainzEntities{
        AREA,
        ARTIST,
        EVENT,
        GENRE,
        INSTRUMENT,
        LABEL,
        PLACE,
        RECORDING,
        RELEASE,
        RELEASE_GROUP,
        SERIES,
        WORK,
        URL
    }

    @LearningTest
    @Test
    fun howTheMusicBrainzApiWorks(){
//        lookup:   /<ENTITY_TYPE>/<MBID>?inc=<INC>
//        browse:   /<RESULT_ENTITY_TYPE>?<BROWSING_ENTITY_TYPE>=<MBID>&limit=<LIMIT>&offset=<OFFSET>&inc=<INC>
//        search:   /<ENTITY_TYPE>?query=<QUERY>&limit=<LIMIT>&offset=<OFFSET>
//        e.g /title?query=<QUERY>&limit=<LIMIT>&offset=<OFFSET>
//        val rootUrl = "https://musicbrainz.org/ws/2/"

        val recording = Recording()
        recording.search("man of your word")

        var results = recording.firstSearchResultPage
        var pagesAfterFirstResultFound = 0
        var pagesCovered = 0
        checkIfArtistMatches(results, pagesAfterFirstResultFound)

        while (recording.hasMore()){
            results = recording.nextSearchResultPage

            checkIfArtistMatches(results, pagesAfterFirstResultFound)
            pagesCovered++
            if (pagesCovered >= 2){
                println("stopping now...covered " + pagesCovered + "pages")
                break;
            }
        }


    }

    private fun checkIfArtistMatches(results: MutableList<RecordingResultWs2>, pagesAfterFirstResultFound: Int) {
        var pagesAfterFirstResultFound1 = pagesAfterFirstResultFound
        for (recordingResult in results) {

            if (recordingResult.recording.artistCreditString.toLowerCase().contains("chandler moore")) {
                println("yaay!" + recordingResult.recording.title + recordingResult.recording.artistCredit + recordingResult.recording.duration + recordingResult.recording.releases)
                pagesAfterFirstResultFound1++
                if (pagesAfterFirstResultFound1 > 2) {
                    println("stop now")
                }
            }
        }
    }
}