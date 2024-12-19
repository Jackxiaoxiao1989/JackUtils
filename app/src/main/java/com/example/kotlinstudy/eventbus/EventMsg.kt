package com.example.kotlinstudy.eventbus

import androidx.room.ColumnInfo

open class EventMsg {
    var message: String=""
    var data : String = ""
    var type = 0
    var where = 0
    var pos = 0


    constructor(message: String, type: Int) {
        this.message = message
        this.type = type
    }

    constructor(where: Int, pos: Int, message: String) {
        this.message = message
        this.where = where
        this.pos = pos
    }
    constructor(where: Int, pos: Int, message: String,data:String) {
        this.message = message
        this.where = where
        this.pos = pos
        this.data = data
    }
    constructor(){

    }
}
