package offlineMusicLibrary.musicFilesPreparation

import offlineMusicLibrary.fileSystemOps.MusicFile
import java.nio.file.Path
import java.time.Year
import offlineMusicLibrary.tagging.TagReader

class MusicFileCreator{

    companion object{
        fun createMusicFileObjectsFromListOfFilePathsToBeProcessed(listOfFilePathsToBeProcessed: MutableList<Path>): MutableList<MusicFile> {
            val musicList = mutableListOf<MusicFile>()

            for (path: Path in listOfFilePathsToBeProcessed) {
                val tags = TagReader().readID3TagsFromFilePath(path)

                val newMusicFile = musicFileFactory(path, tags)
                musicList.add(newMusicFile)
            }

            return musicList
        }

        fun musicFileFactory(path: Path, tags: HashMap<String, String>): MusicFile {
            return MusicFile(
                path,
                tags.getOrDefault("title", ""),
                tags.getOrDefault("album", ""),
                tags.getOrDefault("albumArtist", ""),
                tags.getOrDefault("contributingArtists", "").split('/'),
                tags.getOrDefault("genre", ""),
                Year.parse(
                    tags.getOrDefault("year", "0000").split('-')[0]
                ), // tag comes in the format yyyy-mm-dd so split it with '-' as the delimiter and take only the first sclice i.e  YYYY
                tags.getOrDefault("trackNumber", "0").toInt(),
                tags.getOrDefault("trackLength", ""),
                tags.getOrDefault("numberOfEmptyFields", "0").toInt()
            )
        }
    }

}