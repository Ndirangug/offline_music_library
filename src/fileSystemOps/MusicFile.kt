package offlineMusicLibrary.fileSystemOps

import java.nio.file.Path
import java.time.Year

data class MusicFile(
    val filePath: Path,
    val title: String,
    val album: String,
    val albumArtist: String,
    val contributingArtists: List<String>,
    val genre: String,
    val year: Year,
    val trackNumber: Int,
    var numberOfEmptyFields: Int
    ) {

}