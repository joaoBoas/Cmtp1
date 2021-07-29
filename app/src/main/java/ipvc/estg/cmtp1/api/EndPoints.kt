package ipvc.estg.cmtp1.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {


    @FormUrlEncoded
    @POST("users/login")
    fun login(@Field( "username") username: String?, @Field("password") password: String?): Call<User>
    @GET("report")
    fun getReports(): Call<List<Report>>
}