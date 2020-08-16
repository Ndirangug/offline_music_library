package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile

class MusicbrainzSearch: DatabaseSearch() {

    fun Companion.attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
        val searchResults = search(musicFile)
        update(musicFile)

        return musicFile
    }

    private fun Companion.search(musicFile: MusicFile): HashMap<String, String> {

        return hashMapOf()
    }

    private fun Companion.update(musicFile: MusicFile) {}


}