package offlineMusicLibrary.fileSystemOps

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.toList

enum class FileTypes{
    MP3, OGG, WAV, ACC
}

class MusicFilesLoader(var location: String, var allowableFileTypes: HashSet<FileTypes>) {

    private val listOfFilePaths = mutableListOf<String>()


    fun loadFilesToBeProcessed(): List<MusicFile>  {
        //TODO implement loadFilesToBeProcessed

        val listOfFiles = fetchListOfFilePaths()
        val unsupportedAndSupportedMap = filterFilesAccordingToSupportedAudioFormats(listOfFiles)
        println(unsupportedAndSupportedMap.toString())
        return emptyList()
        
    }


    fun fetchListOfFilePaths(): List<Path> {

        val directoryPath = Paths.get(this.location)

        return Files.walk(directoryPath)
            .filter { item -> Files.isRegularFile(item) }
            .toList()
    }

    fun filterFilesAccordingToSupportedAudioFormats(filePathsList: List<Path>): Map<String, List<Path>>{
        //TODO confirm filetypes that support audio tagging and filter
        val filesWithSupportedFormat = mutableListOf<Path>()
        val filesWithUnSupportedFormat = mutableListOf<Path>()

        filePathsList.forEach { item ->
            run {
                if (item.toString().endsWith("mp3")) { //TODO implement regexp
                    filesWithSupportedFormat.add(item)
                } else {
                    filesWithUnSupportedFormat.add(item)
                }
            }
        }

        return mapOf("SUPPORTED" to filesWithSupportedFormat, "UNSUPPORTED" to filesWithUnSupportedFormat)
    }

    fun createMusicFileObjects(): MutableList<MusicFile> {
        val musicList = mutableListOf<MusicFile>()

        for (path: String in listOfFilePaths){
            //TODO implement createMusicFileObjects
        }
        return musicList
    }
}
