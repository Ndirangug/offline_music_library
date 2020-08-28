package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

interface ITextualSearch {

    fun searchUsingFileNameFragments(fragments: List<String>): HashMap<String, String> {
        //inheriting classes/companion objects to implement this
        return hashMapOf()
    }
}