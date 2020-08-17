package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile

class MusicbrainzSearch{

    companion object: IDatabaseSearch{
        override fun attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
            //TODO implement this
            return super.attemptRetrieveMetaTags(musicFile)
        }

        override fun search(musicFile: MusicFile): HashMap<String, String> {
            //TODO implement this
            return super.search(musicFile)
        }

        override fun updateMusicFile(musicFile: MusicFile, searchResults: HashMap<String, String>) {
            //TODO implement this
            super.updateMusicFile(musicFile, searchResults)

        }
    }


}