package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile

interface DatabaseSearch {
    fun attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
        val searchResults = search(musicFile)
        update(musicFile)
        return musicFile
    }

    fun search(musicFile: MusicFile): HashMap<String, String> {
        return hashMapOf()
    }

    fun update(musicFile: MusicFile) {}
}