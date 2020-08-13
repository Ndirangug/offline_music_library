package offlineMusicLibrary.tagging

import offlineMusicLibrary.fileSystemOps.MusicFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import java.time.Year

class TagReader(private var listOfFilePathsToBeProcessed: MutableList<Path>) {

    fun createMusicFileObjectsFromListOfFilePathsToBeProcessed(): MutableList<MusicFile> {
        val musicList = mutableListOf<MusicFile>()

        for (path: Path in listOfFilePathsToBeProcessed) {

            val tags = readID3TagsFromFilePath(path)


            musicList.add(
                MusicFile(
                    path,
                    tags.getOrDefault("title", ""),
                    tags.getOrDefault("album", ""),
                    tags.getOrDefault("albumArtist", ""),
                    tags.getOrDefault("contributingArtists", "").split('/'),
                    tags.getOrDefault("genre", ""),
                    Year.parse(
                        tags.getOrDefault("year", "0000").split('-')[0]
                    ), // tag comes in the format yyyy-mm-dd so split it with '-' as the delimiter and take only the first sclice i.e  YYYY
                    tags.getOrDefault("trackNumber", "0").toInt(),
                    tags.getOrDefault("numberOfEmptyFields", "0").toInt()
                )
            )
        }

        return musicList
    }

    private fun readID3TagsFromFilePath(filePath: Path): HashMap<String, String> {

        val fileHandle = File(filePath.toString())
        val hashMapOfTags: HashMap<String, String>

        if (!fileHandle.isFile) {
            throw FileNotFoundException("Couldn't find file $filePath .Please check that the name is correct")
            //TODO maybe handle this error better
        }

        return try {
            attemptToCreateHashMapFromExistingTags(fileHandle)
        } catch (e: org.jaudiotagger.audio.exceptions.CannotReadException) {
            hashMapOfTags = createHashMapOfEmptyTags()
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
                "numberOfEmptyFields" to "0"
            )
        } else {
            createHashMapOfEmptyTags()
        }

        validateHashMapOfTags(hashMap)
        updateNumberOfEmptyFields(hashMap)
        return hashMap
    }

    private fun createHashMapOfEmptyTags(): HashMap<String, String> {
        return hashMapOf(
            "title" to "",
            "album" to "",
            "albumArtist" to "",
            "contributingArtists" to "",
            "genre" to "",
            "year" to "0000",
            "trackNumber" to "0",
            "numberOfEmptyFields" to "7"
        )
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