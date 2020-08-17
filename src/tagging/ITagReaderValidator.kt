package offlineMusicLibrary.tagging

interface ITagReaderValidator {

    fun validateHashMapOfTags(hashMap: HashMap<String, String>) {

        hashMap.forEach { (key, value) ->
            run {
                if (key == "year" && value.length < 4) {
                    hashMap[key] = "0000"
                } else if (key == "trackNumber" && value == "") {
                    hashMap[key] = "0"
                } else if (value.toLowerCase().contains("unknown")) {
                    hashMap[key] = ""
                }

            }
        }
    }

    fun updateNumberOfEmptyFields(hashMapOfTags: HashMap<String, String>) {
        hashMapOfTags["numberOfEmptyFields"] = countEmptyTagFields(hashMapOfTags).toString()
    }

    private fun countEmptyTagFields(hashMapOfTags: HashMap<String, String>): Int {
        var count = 0

        for (entry in hashMapOfTags) {
            if ((entry.value == "" || entry.value == "0" || entry.value == "0000") && (entry.key != "numberOfEmptyFields")) //don't include key 'numberOfEmptyFields' in count
                count++
        }
        return count
    }
}