package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.onlineTagMatching.preProcessing.PreProcessor

class MusicbrainzSearch{

    companion object: IDatabaseSearch{

        //only override methods search() and searchUsingFileNameFragments() to suite this specific use case.
        //Only these two need to be customized.The rest of the methods, from IDatabaseSearch, the inherited implementations already work perfectly
        //for all use cases

        override fun search(musicFile: MusicFile): HashMap<String, String> {
            //TODO implement this
            //first try fileNameFragments after determining that its not random sequence
            //TODO if not work fallback to pre existing tags
            val fileNameFragments = PreProcessor.breakFileNameIntoChunks(musicFile.filePath.fileName.toString())
            return searchUsingFileNameFragments(fileNameFragments)

        }


        override fun searchUsingFileNameFragments(fragments: List<String>): HashMap<String, String> {
            //TODO implement this
            // search musicbrainz here! heres the spot
            return hashMapOf(
                "title" to "",
                "album" to "",
                "albumArtist" to "",
                "contributingArtists" to "",
                "genre" to "",
                "year" to "0000",
                "trackNumber" to "0",
                "trackLength" to "",
                "numberOfEmptyFields" to "8"
            )
        }
    }


}