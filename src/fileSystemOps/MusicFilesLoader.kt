package offlineMusicLibrary.fileSystemOps

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Year
import kotlin.streams.toList

enum class FileTypes {
    MP3, OGG, WAV, ACC
}

class MusicFilesLoader(var location: String, var allowableFileTypes: HashSet<FileTypes>) {

    private val listOfFilePaths = mutableListOf<Path>()


    fun loadFilesToBeProcessed(): List<MusicFile> {
        //TODO implement loadFilesToBeProcessed
        // for those in filesWithUnSupportedFormat, give optins to handle them if they're valid audio files, else discard, maybe with warning

        val listOfFiles = fetchListOfFilePaths()
        val unsupportedAndSupportedMap = filterFilesAccordingToSupportedAudioFormats(listOfFiles)
        println(unsupportedAndSupportedMap.toString())
        return emptyList()

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

    fun createMusicFileObjects(): MutableList<MusicFile> {
        val musicList = mutableListOf<MusicFile>()

        for (path: Path in listOfFilePaths) {
            //TODO implement createMusicFileObjects
            val tags = readID3TagsFromFilePath(path)

            musicList.add(
                MusicFile(
                    path,
                    tags.getOrDefault("title", ""),
                    tags.getOrDefault("album", ""),
                    tags.getOrDefault("albumArtist", ""),
                    listOf(tags.getOrDefault("contributingArtists", "")),
                    tags.getOrDefault("genre", ""),
                    Year.parse(tags.getOrDefault("year", "")),
                    tags.getOrDefault("trackNumber", "").toInt()
                )
            )
        }

        return musicList
    }

    fun readID3TagsFromFilePath(filePath: Path): HashMap<String, String> {
        //TODO implement createMusicFileObjects

        return emptyMap<String, String>() as HashMap<String, String>

    }
}
