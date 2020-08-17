package offlineMusicLibrary.tagging

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path

class TagReader : ITagReaderValidator {



     fun readID3TagsFromFilePath(filePath: Path): HashMap<String, String> {

        val fileHandle = File(filePath.toString())
        val hashMapOfTags: HashMap<String, String>

        if (!fileHandle.isFile) {
            throw FileNotFoundException("Couldn't find file $filePath .Please check that the name is correct")
            //TODO maybe handle this error better
        }

        return try {
            attemptToCreateHashMapFromExistingTags(fileHandle)
        } catch (e: org.jaudiotagger.audio.exceptions.CannotReadException) {
            hashMapOfTags = createHashMapOfEmptyTags(fileHandle)
            validateHashMapOfTags(hashMapOfTags)
            hashMapOfTags
        }

    }

    private fun attemptToCreateHashMapFromExistingTags(fileHandle: File): HashMap<String, String> {
        val hashMap: HashMap<String, String>
        val f = AudioFileIO.read(fileHandle)
        val tag: Tag? = f.tag


        hashMap = if (tag != null) {
            hashMapOf(
                "title" to tag.getFirst(FieldKey.TITLE),
                "album" to tag.getFirst(FieldKey.ALBUM),
                "albumArtist" to tag.getFirst(FieldKey.ALBUM_ARTIST),
                "contributingArtists" to tag.getFirst(FieldKey.ARTISTS),
                "genre" to tag.getFirst(FieldKey.GENRE),
                "year" to tag.getFirst(FieldKey.YEAR),
                "trackNumber" to tag.getFirst(FieldKey.TRACK),
                "numberOfEmptyFields" to "0",
                "trackLength" to tryToReadTrackLength(f)
            )
        } else {
            createHashMapOfEmptyTags(fileHandle)
        }

        validateHashMapOfTags(hashMap)
        updateNumberOfEmptyFields(hashMap)
        return hashMap
    }



    private fun createHashMapOfEmptyTags(f: File): HashMap<String, String> {
        return  try {
            val audioFile = AudioFileIO.read(f)

            hashMapOf(
                "title" to "",
                "album" to "",
                "albumArtist" to "",
                "contributingArtists" to "",
                "genre" to "",
                "year" to "0000",
                "trackNumber" to "0",
                "trackLength" to tryToReadTrackLength(audioFile),
                "numberOfEmptyFields" to "7"
            )

        }catch (e: Exception){
            hashMapOf(
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
        }



    }

    private fun tryToReadTrackLength(audioFile: AudioFile?): String {
        return try {
            val audioHeader = audioFile?.audioHeader
            audioHeader?.trackLength?.toString() ?: ""

        } catch (e: Exception) {
            ""
        }
    }

    private fun validateHashMapOfTags(hashMap: HashMap<String, String>) {

        hashMap.forEach { (key, value) ->
            run {
                if (key == "year" && value.length < 4) {
                    hashMap[key] = "0000"
                } else if (key == "trackNumber" && value == "") {
                    hashMap[key] = "0"
                } else if (value.toLowerCase().contains("unknown")) {
                    hashMap[key] = ""
                }

            }
        }
    }

    private fun updateNumberOfEmptyFields(hashMapOfTags: HashMap<String, String>) {
        hashMapOfTags["numberOfEmptyFields"] = countEmptyTagFields(hashMapOfTags).toString()
    }

    private fun countEmptyTagFields(hashMapOfTags: HashMap<String, String>): Int {
        var count = 0

        for (entry in hashMapOfTags) {
            if ((entry.value == "" || entry.value == "0" || entry.value == "0000") && (entry.key != "numberOfEmptyFields")) //don't include key 'numberOfEmptyFields' in count
                count++
        }
        return count
    }


}