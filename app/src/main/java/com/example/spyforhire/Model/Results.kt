package com.example.spyforhire.Model

import android.provider.Contacts

class Results {
    var business_status: String? = null
    var geometry: Geometry? = null
    var icon: String? = null
    var name: String? = null
    var opening_hours: OpeningHours? = null
    var photos: Array<Photo>? = null
    var place_id: String? = null
    var id:String?=null
    var plus_code: PlusCode? = null
    var price_level = 0
    var rating = 0.0
    var reference: String? = null
    var scope: String? = null
    var types: Array<String>? = null
    var user_ratings_total = 0
    var vicinity: String? = null
}