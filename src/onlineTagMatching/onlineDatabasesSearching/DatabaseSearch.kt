package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile

abstract class DatabaseSearch{


     companion object {

      @JvmStatic
      fun attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
          val searchResults = search(musicFile)
           update(musicFile)
           return musicFile
       }

       @JvmStatic
       protected fun search(musicFile: MusicFile): HashMap<String, String> {
           return hashMapOf()
       }

       @JvmStatic
       protected fun update(musicFile: MusicFile) {}
    }
}