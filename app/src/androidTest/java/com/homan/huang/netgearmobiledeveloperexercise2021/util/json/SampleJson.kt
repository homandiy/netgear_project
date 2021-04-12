package com.homan.huang.netgearmobiledeveloperexercise2021.util.json

// simulate real manifest data
fun getManifestJson() : String {
    return """
{
    "manifest": [
        [
            "a"
        ],
        [
            "b",
            "c"
        ],
        [
            "d",
            "e",
            "f"
        ]
    ]
}
    """.trimIndent()
}

// simulate bad manifest data
fun getBlankManifestJson() : String {
    return """
{
    "manifest": []
}
    """.trimIndent()
}

// simulate real image data
fun getImageJson(): String {
    return """
{
    "name": "Sample Sample Sample",
    "url": "http://afternoon-bayou-28316.herokuapp.com/images/AdobeStock_391155466.jpg",
    "type": "jpg",
    "width": 960,
    "height": 540
}
    """.trimIndent()
}