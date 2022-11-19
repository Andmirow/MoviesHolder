package com.example.moviesholder.domain

class Filter {

    var token = "54PEKD1-QV741T0-NHKSR4H-2JXE7A4"

        set(value) {
            field = value
            page = "1"
        }

    var search = "1-10"
        set(value) {
            field = value
            page = "1"
        }

    var field = "rating.kp"
        set(value) {
            field = value
            page = "1"
        }

    var sortedField = "rating.kp"
        set(value) {
            field = value
            page = "1"
        }

    var sortedType = "-1"
        set(value) {
            field = value
            page = "1"
        }


    var page = "1"

    var limit = "30"

    fun naxtPage(){

    }



}