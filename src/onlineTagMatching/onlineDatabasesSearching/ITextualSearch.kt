package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

interface ITextualSearch {

    fun searchUsingFileNameFragments(fragment: List<String>): HashMap<String, String> {
            return hashMapOf()
    }
}