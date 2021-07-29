package ipvc.estg.cmtp1.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {


    @FormUrlEncoded
    @POST("users/login")
    fun login(@Field( "username") username: String?, @Field("password") password: String?): Call<User>
    @GET("report")
    fun getReports(): Call<List<Report>>
    @FormUrlEncoded
    @POST("report/addReport")
    fun addReport(@Field("title") title: String,
                  @Field("description") description: String,
                  @Field("lat") lat: Float,
                  @Field("lng") lng: Float,
                  @Field("date_creation") date_creation: String,
                  @Field("type") type: String,
                  @Field("user_id") user_id: Int
    ): Call<Report>

}