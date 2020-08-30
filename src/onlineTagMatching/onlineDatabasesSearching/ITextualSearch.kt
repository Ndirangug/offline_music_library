package offlineMusicLibrary.onlineTagMatching.onlineDatabasesSearching

interface ITextualSearch {

    open var fileNameFragments: List<String>

    fun searchUsingFileNameFragments(): HashMap<String, String> {
        //inheriting classes/companion objects to implement this
        return hashMapOf()
    }
}