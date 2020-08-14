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
        assertListEqual(actual, expected)

    }

    private fun setUpUnSortedList(): MutableList<MusicFile> {
        val folderWithFiles = Paths.get("test_assets/").toAbsolutePath().toString()
        val allowableFileTypes = hashSetOf(FileTypes.MP3, FileTypes.OGG)

        val musicFilesLoader = MusicFilesLoader(folderWithFiles, allowableFileTypes)
        val listOfFilePathsToBeProcessed = musicFilesLoader.getMusicFilesToBeProcessed()
        return TagReader(listOfFilePathsToBeProcessed).createMusicFileObjectsFromListOfFilePathsToBeProcessed()
    }
}