package com.example.app_daily.Class

data class User(var email:String="",var name:String="",var password:String="",var phone:String="",var photo_Uri:String?="")
{
    public fun toMap():Map<String,Any?>
    {
        return mapOf(
            "email" to email,
            "name" to name,
            "password" to password,
            "phone" to phone,
            "photo_Uri" to photo_Uri
        )
    }
}