package tagMatching.preProcessing

import assertions.IMusicListAssertions
import offlineMusicLibrary.fileSystemOps.FileTypes
import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.fileSystemOps.MusicFilesLoader
import offlineMusicLibrary.tagMatching.preProcessing.PreProcessor
import offlineMusicLibrary.tagging.TagReader
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import java.time.Year

internal class PreProcessorTest: IMusicListAssertions{

    @Test
    fun sortMusicListFromLeastTaggedToMostTagged() {
        val unSortedList = setUpUnSortedList()

        val expected = mutableListOf(
            MusicFile(
                filePath = Paths.get("test_assets/(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3").toAbsolutePath(),
                title = "",
                album = "",
                albumArtist = "",
                contributingArtists = listOf<String>(),
                genre = "",
                year = Year.parse("0000"),
                trackNumber = 0,
                numberOfEmptyFields = 7
            ),
            MusicFile(
                filePath = Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath(),
                title = "",
                album = "",
                albumArtist = "",
                contributingArtists = listOf<String>(),
                genre = "",
                year = Year.parse("0000"),
                trackNumber = 0,
                numberOfEmptyFields = 7
            ),
            MusicFile(
                filePath = Paths.get("test_assets/14 Bethel Music, Bethany Wohrle & Dante Bowe - Prepare The Way.mp3").toAbsolutePath(),
                title = "Prepare The Way",
                album = "Revival's In The Air",
                albumArtist = "Bethel Music",
                contributingArtists = listOf<String>("Bethel Music", "Bethany Wohrle", "Dante Bowe" ),
                genre = "",
                year = Year.parse("2020"),
                trackNumber = 14,
                numberOfEmptyFields = 1
            )
        )

        val actual = PreProcessor.sortLeastTaggedToMostTagged(unSortedList)
        assertListEqual(expected, actual)

    }

    private fun setUpUnSortedList(): MutableList<MusicFile> {
        val folderWithFiles = Paths.get("test_assets/").toAbsolutePath().toString()
        val allowableFileTypes = hashSetOf(FileTypes.MP3, FileTypes.OGG)

        val musicFilesLoader = MusicFilesLoader(folderWithFiles, allowableFileTypes)
        val listOfFilePathsToBeProcessed = musicFilesLoader.getMusicFilesToBeProcessed()
        return TagReader(listOfFilePathsToBeProcessed).createMusicFileObjectsFromListOfFilePathsToBeProcessed()
    }

    @Test
    fun breakFileNameIntoChunks(): Unit {
        val fileName1 = "Cory_Asbury_Endless_Alleluia.hd.ogg"
        val fileName2 = "(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3"

        val expected1 = listOf(
            "cory",
            "asbury",
            "endless",
            "alleluia",
            "hd",
            "cory asbury",
            "asbury endless",
            "endless alleluia",
            "alleluia hd",
            "cory asbury endless",
            "asbury endless alleluia",
            "endless alleluia hd",
            "cory asbury endless alleluia",
            "asbury endless alleluia hd",
            "cory asbury endless alleluia hd"
        )

        val expected2 = listOf(
            "man",
            "of",
            "your",
            "word",
            "feat",
            "chandler",
            "moore",
            "kj",
            "scriven",
            "maverick",
            "city",
            "tribl",
            "youtube",
            "converted",
            "man of",
            "of your",
            "your word",
            "word feat",
            "feat chandler",
            "chandler moore",
            "moore kj",
            "kj scriven",
            "scriven maverick",
            "maverick city",
            "city tribl",
            "tribl youtube",
            "youtube converted",
            "man of your",
            "of your word",
            "your word feat",
            "word feat chandler",
            "feat chandler moore",
            "chandler moore kj",
            "moore kj scriven",
            "kj scriven maverick",
            "scriven maverick city",
            "maverick city tribl",
            "city tribl youtube",
            "tribl youtube converted",
            "man of your word",
            "of your word feat",
            "your word feat chandler",
            "word feat chandler moore",
            "feat chandler moore kj",
            "chandler moore kj scriven",
            "moore kj scriven maverick",
            "kj scriven maverick city",
            "scriven maverick city tribl",
            "maverick city tribl youtube",
            "city tribl youtube converted",
            "man of your word feat",
            "of your word feat chandler",
            "your word feat chandler moore",
            "word feat chandler moore kj",
            "feat chandler moore kj scriven",
            "chandler moore kj scriven maverick",
            "moore kj scriven maverick city",
            "kj scriven maverick city tribl",
            "scriven maverick city tribl youtube",
            "maverick city tribl youtube converted",
            "man of your word feat chandler",
            "of your word feat chandler moore",
            "your word feat chandler moore kj",
            "word feat chandler moore kj scriven",
            "feat chandler moore kj scriven maverick",
            "chandler moore kj scriven maverick city",
            "moore kj scriven maverick city tribl",
            "kj scriven maverick city tribl youtube",
            "scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore",
            "of your word feat chandler moore kj",
            "your word feat chandler moore kj scriven",
            "word feat chandler moore kj scriven maverick",
            "feat chandler moore kj scriven maverick city",
            "chandler moore kj scriven maverick city tribl",
            "moore kj scriven maverick city tribl youtube",
            "kj scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore kj",
            "of your word feat chandler moore kj scriven",
            "your word feat chandler moore kj scriven maverick",
            "word feat chandler moore kj scriven maverick city",
            "feat chandler moore kj scriven maverick city tribl",
            "chandler moore kj scriven maverick city tribl youtube",
            "moore kj scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore kj scriven",
            "of your word feat chandler moore kj scriven maverick",
            "your word feat chandler moore kj scriven maverick city",
            "word feat chandler moore kj scriven maverick city tribl",
            "feat chandler moore kj scriven maverick city tribl youtube",
            "chandler moore kj scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore kj scriven maverick",
            "of your word feat chandler moore kj scriven maverick city",
            "your word feat chandler moore kj scriven maverick city tribl",
            "word feat chandler moore kj scriven maverick city tribl youtube",
            "feat chandler moore kj scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore kj scriven maverick city",
            "of your word feat chandler moore kj scriven maverick city tribl",
            "your word feat chandler moore kj scriven maverick city tribl youtube",
            "word feat chandler moore kj scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore kj scriven maverick city tribl",
            "of your word feat chandler moore kj scriven maverick city tribl youtube",
            "your word feat chandler moore kj scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore kj scriven maverick city tribl youtube",
            "of your word feat chandler moore kj scriven maverick city tribl youtube converted",
            "man of your word feat chandler moore kj scriven maverick city tribl youtube converted"
        )

        val actual1 = PreProcessor.breakFileNameIntoChunks(fileName1)
        println(actual1)
        val actual2 = PreProcessor.breakFileNameIntoChunks(fileName2)
        println(actual2)

        assertListEqual<String>(expected1, actual1)
        assertListEqual<String>(expected2, actual2)
    }


}