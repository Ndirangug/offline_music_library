package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile

interface ITextualSearch {

    open var fileNameFragments: List<String>

    fun searchUsingFileNameFragments(musicFile: MusicFile): Unit {
        //inheriting classes/companion objects to implement this

    }
}