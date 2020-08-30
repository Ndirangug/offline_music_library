package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

import offlineMusicLibrary.fileSystemOps.MusicFile
import offlineMusicLibrary.onlineTagMatching.preProcessing.PreProcessor
import org.musicbrainz.controller.Recording
import org.musicbrainz.model.searchresult.RecordingResultWs2

class MusicbrainzSearch{



    companion object: IDatabaseSearch, ITextualSearch{

        //only override methods search() and searchUsingFileNameFragments() to suite this specific use case.
        //Only these two need to be customized.The rest of the methods, from IDatabaseSearch, the inherited implementations already work perfectly
        //for all use cases
        override var fileNameFragments = listOf<String>()

        override fun search(musicFile: MusicFile): HashMap<String, String> {
            //TODO implement this
            //first try fileNameFragments after determining that its not random sequence
            //if not work fallback to pre existing tags
            fileNameFragments = PreProcessor.breakFileNameIntoChunks(musicFile.filePath.fileName.toString())
            fileNameFragments =  fileNameFragments.sortedByDescending{it.length}
            return searchUsingFileNameFragments()

        }

         override fun searchUsingFileNameFragments(): HashMap<String, String> {
            //TODO implement this
             var pagesCovered = 0


          outerloop@ for (fragment in fileNameFragments){
                val recording = Recording()
                recording.search(fragment)

                var results = recording.firstSearchResultPage

                checkIfArtistMatches(results)

                while (recording.hasMore()){
                    results = recording.nextSearchResultPage

                    checkIfArtistMatches(results)
                    pagesCovered++

                    if (pagesCovered >= 2){
                        println("stopping now...covered " + pagesCovered + "pages")
                        break@outerloop
                    }
                }


            }

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

        private fun checkIfArtistMatches(results: MutableList<RecordingResultWs2>) {

            for (recordingResult in results) {
               for (fragment in fileNameFragments){
                   if (recordingResult.recording.artistCreditString.toLowerCase().contains(fragment)) {

                       //println("yaay!" + recordingResult.recording.title + recordingResult.recording.artistCreditString + recordingResult.recording.duration + recordingResult.recording.releases)
                       checkIfTitleMatches(recordingResult)

                   }
               }


            }
        }

        private fun checkIfTitleMatches(recordingResult: RecordingResultWs2) {
            for (fragment in fileNameFragments) {
                if (recordingResult.recording.title.toLowerCase().contains(fragment)) {
                    println("yaay!" + recordingResult.recording.title + recordingResult.recording.artistCredit + recordingResult.recording.duration + recordingResult.recording.releases)
                    println()
                }
            }
        }
    }


}