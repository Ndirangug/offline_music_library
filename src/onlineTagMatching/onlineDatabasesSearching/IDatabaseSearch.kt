package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.musicFilesPreparation.MusicFileCreator

interface IDatabaseSearch {

    var preliminaryResults: List<HashMap<String, String>>

    fun attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
        val searchResults = search(musicFile)
       return updateMusicFile(musicFile, searchResults)
    }

    fun search(musicFile: MusicFile): HashMap<String, String> {
        //inheriting classes/companion objects to implement this
        return hashMapOf()
    }

    fun updateMusicFile(oldMusicFile: MusicFile, searchResults: HashMap<String, String>): MusicFile {
        return MusicFileCreator.musicFileFactory(oldMusicFile.filePath, searchResults)
    }
}