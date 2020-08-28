package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

interface ITextualSearch {

    fun searchUsingFileNameFragments(fragments: List<String>): HashMap<String, String> {
            return hashMapOf()
    }
}