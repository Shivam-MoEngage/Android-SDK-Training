package com.example.moengage_newapp.util

import kotlin.random.Random

class Helper {

    companion object {

        private val _base62Char =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()
        private val random = Random

        fun getUid(): String {
            var uid: String = ""
            for (i in 1..16) {
                uid += (_base62Char[random.nextInt(62)])
            }
            return uid
        }

        val userPassMap = mapOf<String, String>(
            "karan" to "arjun",
            "ram" to "sita",
            "romeo" to "juliet",
            "harry" to "potter"
        )
    }


}