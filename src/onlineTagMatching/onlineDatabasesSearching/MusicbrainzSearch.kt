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
        override var preliminaryResults = listOf(hashMapOf<String, String>())
        var matchesFound = 0

        override fun search(musicFile: MusicFile): HashMap<String, String> {
            //first try fileNameFragments after determining that its not random sequence
            //if not work fallback to pre existing tags
            fileNameFragments = PreProcessor.breakFileNameIntoChunks(musicFile.filePath.fileName.toString())
            fileNameFragments =  fileNameFragments.sortedByDescending{it.length}

            searchUsingFileNameFragments(musicFile)
            for (result in preliminaryResults){
                println(result)
            }
            return this.preliminaryResults[0]

        }

         override fun searchUsingFileNameFragments(musicFile: MusicFile): Unit {

            for (fragment in fileNameFragments){
                val recording = Recording()
                recording.search(fragment)

                var results = recording.firstSearchResultPage

                checkIfArtistMatches(results)

                if (this.matchesFound > 10){
                  break
                }
            }

             cleanUpResults()

        }

        private fun checkIfArtistMatches(results: MutableList<RecordingResultWs2>): Boolean {
            var foundMatch = false

            for (recordingResult in results) {

               for (fragment in fileNameFragments){
                   if (recordingResult.recording.artistCreditString.toLowerCase().contains(fragment)) {

                       //println("yaay!" + recordingResult.recording.title + recordingResult.recording.artistCreditString + recordingResult.recording.duration + recordingResult.recording.releases)
                      foundMatch =  checkIfTitleMatches(recordingResult)

                       break

                   }
               }


            }

            return foundMatch
        }

        private fun checkIfTitleMatches(recordingResult: RecordingResultWs2): Boolean {
            var foundMatch = false

            for (fragment in fileNameFragments) {
                if (recordingResult.recording.title.toLowerCase().contains(fragment)) {
                    foundMatch = true
                    this.matchesFound++

                    try {
                        preliminaryResults += createHashMapOfTags(
                            title = recordingResult.recording.title,
                            album = recordingResult.recording.releases[0].title,
                            albumArtist = recordingResult.recording.releases[0].artistCreditString,
                            contributingArtists = recordingResult.recording.artistCredit.nameCredits.toString().replace(" & , ", "/"),
                            genre = "",
                            year = recordingResult.recording.releases[0].year,
                            trackNumber = "0", //TODO find a way to retrieve this
                            //trackLength = (recordingResult.recording.durationInMillis / 1000).toString(),
                            score = recordingResult.score.toString()

                        )
                    }catch (e: Exception){
                        println(e)
                    }

                    break
                }
            }

            return foundMatch
        }

        private fun createHashMapOfTags(
            title: String = "",
            album: String = "",
            albumArtist: String = "",
            contributingArtists: String = "",
            genre: String = "",
            year: String = "",
            trackNumber: String = "0",
            trackLength: String = "",
            numberOfEmptyFields: String = "",
            score: String
        ): HashMap<String, String> {
            return hashMapOf(
                "title" to title,
                "album" to album,
                "albumArtist" to albumArtist,
                "contributingArtists" to contributingArtists,
                "genre" to genre,
                "year" to year,
                "trackNumber" to trackNumber,
                "trackLength" to trackLength,
                "numberOfEmptyFields" to "0",
                "score" to score
            )
        }

        private fun cleanUpResults() {
            val noDuplicates = this.preliminaryResults.toHashSet()
            this.preliminaryResults = noDuplicates.sortedByDescending { it["score"]?.toInt() }
        }

    }


}