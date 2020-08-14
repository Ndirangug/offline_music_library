package offlineMusicLibrary.tagMatching.preProcessing

import offlineMusicLibrary.fileSystemOps.MusicFile

class PreProcessor() {

    companion object{
        fun sortLeastTaggedToMostTagged(musicList: MutableList<MusicFile>): MutableList<MusicFile> {
            musicList.sortByDescending { it.numberOfEmptyFields }

            return musicList
        }

        fun breakFileNameIntoChunks(fileName: String): List<String> {
            //TODO implement this
            return emptyList()
        }
    }


}