import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login") val username: String,
    @SerializedName("created_at") val createdAt: String,
)

data class UserPublicRepo(
    @SerializedName("name") val repoName: String,
)

data class User(
    val username: String,
    val followersCount: Int,
    val followingsCount: Int,
    val createdAt: String,
    val publicRepos: List<String>
)
