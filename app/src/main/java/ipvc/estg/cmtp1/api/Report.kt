package ipvc.estg.cmtp1.api

import java.util.*

data class Report (
    val id: Int,
    val name: String,
    val description: String,
    val lat: Float,
    val lng: Float,
    val users_id: Int
)