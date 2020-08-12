package offlineMusicLibrary.fileSystemOps

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Year
import kotlin.streams.toList


enum class FileTypes {
    MP3, OGG, WAV, ACC, MP4, M4A, M4P, WMA, FLAC, AIFF, DSF
}

class MusicFilesLoader(private var location: String, private var allowableFileTypes: HashSet<FileTypes>) {


    private lateinit var mapOfUnsupportedAndSupportedFiles: Map<String, List<Path>>

    fun getMusicFilesToBeProcessed(): MutableList<Path> {
        populatemapOfUnsupportedAndSupportedFiles()

        return mapOfUnsupportedAndSupportedFiles["FILES_WITH_SUPPORTED_FILE_FORMAT"] as MutableList<Path>

    }

    private fun populatemapOfUnsupportedAndSupportedFiles() {
        //TODO implement loadFilesToBeProcessed
        // for those in filesWithUnSupportedFormat, give optins to handle them if they're valid audio files, else discard, maybe with warning

        val listOfFiles = fetchListOfFilePaths()

        mapOfUnsupportedAndSupportedFiles = filterFilesAccordingToSupportedAudioFormats(listOfFiles)


    }


    private fun fetchListOfFilePaths(): List<Path> {

        val directoryPath = Paths.get(this.location)

        return Files.walk(directoryPath)
            .filter { item -> Files.isRegularFile(item) }
            .toList()
    }

    private fun filterFilesAccordingToSupportedAudioFormats(filePathsList: List<Path>): Map<String, List<Path>> {

        val filesWithSupportedFormat = mutableListOf<Path>()
        val filesWithUnSupportedFormat = mutableListOf<Path>()

        filePathsList.forEach { filePath ->
            run {
                var hasSupportedFileType = false

                for (fileType in allowableFileTypes) {
                    if (filePath.toString().toLowerCase().endsWith(fileType.toString().toLowerCase())) {

                        hasSupportedFileType = true
                        filesWithSupportedFormat.add(filePath)
                        break
                    }
                }

                if (!hasSupportedFileType) {
                    filesWithUnSupportedFormat.add(filePath)
                }
            }

        }

        return mapOf(
            "FILES_WITH_SUPPORTED_FILE_FORMAT" to filesWithSupportedFormat,
            "FILES_WITH_UNSUPPORTED_FILE_FORMAT" to filesWithUnSupportedFormat
        )
    }


}
