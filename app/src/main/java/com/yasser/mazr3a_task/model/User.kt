package com.yasser.mazr3a_task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {


    var email:String=""

    var name:String=""
    @PrimaryKey
    var phone:String=""
    var homeLocation:String=""
    var officeLocation:String=""
    var otherLocation:String=""


}