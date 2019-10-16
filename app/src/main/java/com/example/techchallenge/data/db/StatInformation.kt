package com.example.techchallenge.data.db

class StatInformation(val location: Location, val category: Category)

enum class Location(val location: String) {
    NAVIGATE_HOME("navigate_home"),
    NAVIGATE_MY_SPORT("navigate_my_sport"),
    NAVIGATE_GUIDE("navigate_guide"),
    NAVIGATE_ALL_SPORT("navigate_all_sport")
}

enum class Category(val category: String) {
    SIGN_IN("sign_in"),
    NAVIGATION("navigation"),
    CONTENT("content")
}