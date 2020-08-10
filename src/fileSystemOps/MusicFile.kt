package offlineMusicLibrary.fileSystemOps

import java.nio.file.Path
import java.time.Year

data class MusicFile(
    val filePath: Path,
    var title: String,
    var album: String,
    var albumArtist: String,
    var contributingArtists: List<String>,
    var genre: String,
    var year: Year,
    var trackNumber: Int

    ) {

}