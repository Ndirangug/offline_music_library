package offlineMusicLibrary.onlineTagMatching.preProcessing

import offlineMusicLibrary.fileSystemOps.MusicFile

class PreProcessor() {

    companion object {
        fun sortLeastTaggedToMostTagged(musicList: MutableList<MusicFile>): MutableList<MusicFile> {
            musicList.sortByDescending { it.numberOfEmptyFields }

            return musicList
        }

        fun breakFileNameIntoChunks(fileName: String): List<String> {

            var fileName = tidyUpFileName(fileName)
            val initialChunksFromFileName = fileName.split(" ")
            var numberOfWords = initialChunksFromFileName.lastIndex + 1

            return splitIntoAllPossibleSubstrings(initialChunksFromFileName, numberOfWords)

        }

        private fun tidyUpFileName(fileName: String): String {
            var fileName = fileName.substring(0, fileName.length - 3)  //cut out last three chars, safe to assume its a filename extension
            fileName = fileName.replace(Regex("[^A-Za-z]"), " ") //remove all non-alphabetical character
            fileName = fileName.replace(Regex("\\s{1,5}"), " ") // remove consecutive spaces
            fileName = fileName.trim() // remove trailing white space
            fileName = fileName.toLowerCase()
            return fileName
        }

        private fun splitIntoAllPossibleSubstrings(fileNameArray: List<String>, numberOfWords: Int): MutableList<String> {
            val listOfChunks = mutableListOf<String>()
            var chunkBuffer = ""
            // Pick starting point
            for (len in 1..numberOfWords) {

                // Pick ending point
                for (i in 0..numberOfWords - len) {
                    //  Print words from current  starting point to current ending point.
                    val j = i + len - 1
                    for (k in i..j) {
                        chunkBuffer += "${fileNameArray[k]} "

                    }
                    chunkBuffer = chunkBuffer.trim()
                    listOfChunks.add(chunkBuffer)
                    chunkBuffer = ""
                }
            }

            return listOfChunks
        }
    }


}