// IStudyAidlInterface.aidl
package com.example.kotlinstudy;
import com.example.kotlinstudy.StudyInfo;
// Declare any non-default types here with import statements

interface IStudyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
    //        double aDouble, String aString);
        List<StudyInfo> getAllUser();
        int addUser(String name,int age);
        Map getOneUserMap(int index);
}