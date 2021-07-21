package com.example.moengage_newapp.data

import org.jetbrains.annotations.NotNull

data class User(

    @NotNull
    val userName: String,

    @NotNull
    val passWord: String,

    @NotNull
    val uId: String
)
