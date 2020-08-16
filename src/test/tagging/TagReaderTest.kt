package tagging

import assertions.IMusicListAssertions
import offlineMusicLibrary.fileSystemOps.FileTypes
import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.fileSystemOps.MusicFilesLoader
import offlineMusicLibrary.tagging.TagReader
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.Tag
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Year
import java.util.*
import kotlin.test.assertEquals

internal class TagReaderTest : IMusicListAssertions {




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
                trackNumber = 0,
                trackLength = "546",
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
                trackLength = "321",
                numberOfEmptyFields = 1
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
                trackLength = "",
                numberOfEmptyFields = 8
            )
        )

        val actual = TagReader(listOfFilePathsToBeProcessed).createMusicFileObjectsFromListOfFilePathsToBeProcessed()

        assertListEqual(expected, actual)

    }

    private fun setUp(){
        allowableFileTypes = hashSetOf(FileTypes.MP3, FileTypes.OGG)
        musicFilesLoader = MusicFilesLoader(folderWithFiles, allowableFileTypes)
        listOfFilePathsToBeProcessed = musicFilesLoader.getMusicFilesToBeProcessed()
    }

    @Test
    fun tryToReadEmptyAudioHeaderReturnsEmptyString(){
        val filePath = Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath()
        val fileHandle = File(filePath.toString())

        val actualTrackLength = try {
            val f = AudioFileIO.read(fileHandle)
            val audioHeader = f.audioHeader
            audioHeader?.trackLength?.toString() ?: ""

        }catch (e: org.jaudiotagger.audio.exceptions.CannotReadException){
            ""
        }

        val expectedTrackLength = "" // couldn't read audio header

        assertEquals(expectedTrackLength, actualTrackLength)
    }

    @Test
    fun tryToReadAudioHeaderTrackLenthReturnsSeconds(){
        val filePath = Paths.get("test_assets/(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3").toAbsolutePath()
        val fileHandle = File(filePath.toString())

        val actualTrackLength = try {
            val f = AudioFileIO.read(fileHandle)
            val audioHeader = f.audioHeader
            audioHeader?.trackLength?.toString() ?: ""

        }catch (e: org.jaudiotagger.audio.exceptions.CannotReadException){
            ""
        }

        val expectedTrackLength = "546" // 9:05 into seconds

        assertEquals(expectedTrackLength, actualTrackLength)
    }
}