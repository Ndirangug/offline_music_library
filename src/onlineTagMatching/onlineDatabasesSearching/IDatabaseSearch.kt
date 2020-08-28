package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.musicFilesPreparation.MusicFileCreator

interface IDatabaseSearch: ITextualSearch {
    fun attemptRetrieveMetaTags(musicFile: MusicFile): MusicFile {
        val searchResults = search(musicFile)
       return updateMusicFile(musicFile, searchResults)
    }

    fun search(musicFile: MusicFile): HashMap<String, String> {
        //first try fileNameFragments after determining that its not random sequence
        //if not work fallback to pre existing tags
        return hashMapOf()
    }

    fun updateMusicFile(oldMusicFile: MusicFile, searchResults: HashMap<String, String>): MusicFile {
        return MusicFileCreator.musicFileFactory(oldMusicFile.filePath, searchResults)
    }
}