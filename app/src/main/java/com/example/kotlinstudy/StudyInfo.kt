package com.example.kotlinstudy

import android.os.Parcel
import android.os.Parcelable

class StudyInfo(): Parcelable {
    var age = 0
    var name: String? = null

    constructor(name: String?, age: Int):this() {
        this.name = name
        this.age = age
    }

     constructor(parcel: Parcel):this() {
         age = parcel.readInt()
         name = parcel.readString().toString()
     }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(age)
        dest.writeString(name)
    }

    fun readFromParcel(dest: Parcel) {
        name = dest.readString()
        age = dest.readInt()
    }

    companion object CREATOR : Parcelable.Creator<StudyInfo> {
        override fun createFromParcel(parcel: Parcel): StudyInfo {
            return StudyInfo(parcel)
        }

        override fun newArray(size: Int): Array<StudyInfo?> {
            return arrayOfNulls(size)
        }
    }

}