package com.dkb_code_factory.url_shortener.helper

object AlphaNumericIdCreator  {

    public fun generateAlphanumericId(length: Int): String {
        val charset = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return List(length) { charset.random() }.joinToString("")
    }
}