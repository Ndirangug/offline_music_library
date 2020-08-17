package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile

interface IDatabaseSearch {
    fun attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
        val searchResults = search(musicFile)
        updateMusicFile(musicFile, searchResults)

        return musicFile
    }

    fun search(musicFile: MusicFile): HashMap<String, String> {
        return hashMapOf()
    }

    fun updateMusicFile(musicFile: MusicFile, searchResults: HashMap<String, String>) {}
}