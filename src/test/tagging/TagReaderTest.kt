package tagging

import offlineMusicLibrary.fileSystemOps.FileTypes
import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.fileSystemOps.MusicFilesLoader
import offlineMusicLibrary.tagging.TagReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Year
import java.util.*

internal class TagReaderTest {




    private val folderWithFiles =  Paths.get("test_assets/").toAbsolutePath().toString()
    private lateinit var allowableFileTypes: HashSet<FileTypes>
    private lateinit var musicFilesLoader: MusicFilesLoader
    private lateinit var listOfFilePathsToBeProcessed: MutableList<Path>


    @Test
    fun createMusicFileObjectsFromListOfFilePathsToBeProcessed() {
        setUp()

        // existing tags are appropriately populated
        // absent ones are replaced with the predefined default values in TagReader.createHashMapOfEmptyTags()
        val expected = mutableListOf(
            MusicFile(
                filePath = Paths.get("test_assets/(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3").toAbsolutePath(),
                title = "",
                album = "",
                albumArtist = "",
                contributingArtists = listOf<String>(),
                genre = "",
                year = Year.parse("0000"),
                trackNumber = 0
            ),
            MusicFile(
                filePath = Paths.get("test_assets/14 Bethel Music, Bethany Wohrle & Dante Bowe - Prepare The Way.mp3").toAbsolutePath(),
                title = "Prepare The Way",
                album = "Revival's In The Air",
                albumArtist = "Bethel Music",
                contributingArtists = listOf<String>("Bethel Music", "Bethany Wohrle", "Dante Bowe" ),
                genre = "",
                year = Year.parse("2020"),
                trackNumber = 14
            ),
            MusicFile(
                filePath = Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath(),
                title = "",
                album = "",
                albumArtist = "",
                contributingArtists = listOf<String>(),
                genre = "",
                year = Year.parse("0000"),
                trackNumber = 0
            )
        )

        val actual = TagReader(listOfFilePathsToBeProcessed).createMusicFileObjectsFromListOfFilePathsToBeProcessed()

        for (i in actual.indices){
            assertEquals(expected[i].toString(), actual[i].toString())
            //TODO  maybe find a more effecient way for this
        }

    }

    private fun setUp(){
        allowableFileTypes = hashSetOf(FileTypes.MP3, FileTypes.OGG)
        musicFilesLoader = MusicFilesLoader(folderWithFiles, allowableFileTypes)
        listOfFilePathsToBeProcessed = musicFilesLoader.getMusicFilesToBeProcessed()
    }
}