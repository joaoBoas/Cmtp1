package ipvc.estg.cmtp1.api

import java.util.*

data class Report (
    val id: Int,
    val title: String,
    val description: String,
    val lat: Float,
    val lng: Float,
    val user_id: Int
)