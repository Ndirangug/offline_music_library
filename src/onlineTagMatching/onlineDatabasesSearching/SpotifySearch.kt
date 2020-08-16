package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile

class SpotifySearch: DatabaseSearch() {

    fun Companion.attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
        val searchResults = search(musicFile)
        update(musicFile)

        return musicFile
    }

    fun Companion.search(musicFile: MusicFile): HashMap<String, String> {

        return hashMapOf()
    }

    fun Companion.update(musicFile: MusicFile) {}
}