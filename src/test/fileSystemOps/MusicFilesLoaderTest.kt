package fileSystemOps

import offlineMusicLibrary.fileSystemOps.FileTypes
import offlineMusicLibrary.fileSystemOps.MusicFilesLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths

internal class MusicFilesLoaderTest {

    private lateinit var folderWithFiles: String
    private lateinit var allowableFileTypes: HashSet<FileTypes>
    private lateinit var musicFilesLoader: MusicFilesLoader


    @Test
    fun getMusicFilesToBeProcessedReturnsAlistOfPathsFromDirectory() {
        setUp1()

        val expected = mutableListOf(
            Paths.get("test_assets/(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3").toAbsolutePath(),
            Paths.get("test_assets/14 Bethel Music, Bethany Wohrle & Dante Bowe - Prepare The Way.mp3").toAbsolutePath(),
            Paths.get("test_assets/Cory_Asbury_Endless_Alleluia.hd.ogg").toAbsolutePath()
        )

        val actual = musicFilesLoader.getMusicFilesToBeProcessed()

        assertEquals(expected, actual)

    }

    private fun setUp1() {
        folderWithFiles = Paths.get("test_assets/").toAbsolutePath().toString()
        allowableFileTypes = hashSetOf(FileTypes.MP3, FileTypes.OGG)
        musicFilesLoader = MusicFilesLoader(folderWithFiles, allowableFileTypes)

        println(folderWithFiles)
    }

    @Test
    fun correctlyFiltersSupportedAndUnSupported() {
        setUp2()

        // expect musicFilesLoader to filter out .ogg as we specified in setUp2()
        val expected = mutableListOf(
            Paths.get("test_assets/(10) Man of Your Word (feat. Chandler Moore & KJ Scriven) - Maverick City _ TRIBL - YouTube-converted.mp3").toAbsolutePath(),
            Paths.get("test_assets/14 Bethel Music, Bethany Wohrle & Dante Bowe - Prepare The Way.mp3").toAbsolutePath()

        )

        val actual = musicFilesLoader.getMusicFilesToBeProcessed()

        assertEquals(expected, actual)

    }

    private fun setUp2() {
        folderWithFiles = Paths.get("test_assets/").toAbsolutePath().toString()
        allowableFileTypes = hashSetOf(FileTypes.MP3)
        musicFilesLoader = MusicFilesLoader(folderWithFiles, allowableFileTypes)

        println(folderWithFiles)
    }

}
