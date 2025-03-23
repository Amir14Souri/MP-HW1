import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/users/{user}")
    suspend fun getUser(@Path("user") username: String): GithubUser

    @GET("users/{user}/followers")
    suspend fun getUserFollowers(@Path("user") username: String): List<Any>

    @GET("users/{user}/following")
    suspend fun getUserFollowings(@Path("user") username: String): List<Any>

    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") username: String): List<UserPublicRepo>
}