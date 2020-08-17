package tagging

import annotations.LearningTest
import assertions.IMusicListAssertions
import offlineMusicLibrary.tagging.TagReader
import org.jaudiotagger.audio.AudioFileIO
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths
import kotlin.test.assertEquals

internal class TagReaderTest : IMusicListAssertions {

    @Test
    fun readingTagsFromTaggedFileReturnsHashMapWithCorrectValues(){
       val filePath = Paths.get("test_assets/14 Bethel Music, Bethany Wohrle & Dante Bowe - Prepare The Way.mp3").toAbsolutePath()

        val expected =  hashMapOf(
            "title" to "Prepare The Way",
            "album" to "Revival's In The Air",
            "albumArtist" to "Bethel Music",
            "contributingArtists" to "Bethel Music/Bethany Wohrle/Dante Bowe",
            "genre" to "",
            "year" to "2020-05-29",
            "trackNumber" to 14,
            "trackLength" to "321",
            "numberOfEmptyFields" to 1
        )

        val actual = TagReader().readID3TagsFromFilePath(filePath)

        assertMapEqual(expected, actual)

    }

    @Test
    fun readingTagsFromUnreadableFileReturnsEmptyHashMap(){
        val filePath = Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath()

        val expected =  hashMapOf(
            "title" to "",
            "album" to "",
            "albumArtist" to "",
            "contributingArtists" to "",
            "genre" to "",
            "year" to "0000",
            "trackNumber" to "0",
            "trackLength" to "",
            "numberOfEmptyFields" to "8"
        )

        val actual = TagReader().readID3TagsFromFilePath(filePath)


        assertMapEqual(expected, actual)


    }

    @Test
    fun readingTagsFromUntaggedFileReturnsHashMapWithOnlyTrackLenth(){
        val filePath = Paths.get("test_assets/(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3").toAbsolutePath()
        val expected =  hashMapOf(
            "title" to "",
            "album" to "",
            "albumArtist" to "",
            "contributingArtists" to "",
            "genre" to "",
            "year" to "0000",
            "trackNumber" to "0",
            "trackLength" to "546",
            "numberOfEmptyFields" to "7"
        )

        val actual = TagReader().readID3TagsFromFilePath(filePath)

        assertMapEqual(expected, actual)

    }



    @LearningTest
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

    @LearningTest
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