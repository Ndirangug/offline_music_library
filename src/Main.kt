package offlineMusicLibrary

import offlineMusicLibrary.fileSystemOps.*

/**
 * Entry point. No implementations here
 *
*/
fun main() {
    val folderWithFiles = "/media/georgen/LOCAL DISK/George_Ndirangu/Music/"
    val allowableFileTypes = hashSetOf(FileTypes.MP3, FileTypes.OGG)

    val musicFilesLoader = MusicFilesLoader(folderWithFiles, allowableFileTypes)

    musicFilesLoader.loadFilesToBeProcessed()


}