import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

fun main(): Unit = runBlocking {
    while (true) {
        println("1. Add User\n2. Show Users\n3. Search by User\n4. Search by Repo\n5. Exit")
        val choice = readLine()
        when (choice) {
            "1" -> addUser()
            "2" -> showUsers()
        }
    }
}

suspend fun addUser() {
    println("Enter username:")
    val username: String = readLine() ?: ""

    var user = UsersCache.getUser(username)
    if (user != null) {
        println(user)
        return
    }

    try {
        val githubUser: GithubUser = RetrofitClient.github.getUser(username)
        val followersCount = RetrofitClient.github.getUserFollowers(username).count()
        val followingsCount = RetrofitClient.github.getUserFollowings(username).count()
        val repos = RetrofitClient.github.getUserRepos(username)
        val repoNames = repos.map { it.repoName }
        user = User(githubUser.username, followersCount, followingsCount, githubUser.createdAt, repoNames)
        UsersCache.addUser(user)
        println("$username added successfully:")
        println(user)
    } catch (e: HttpException) {
        if (e.message == "HTTP 404")
            println("User not found")
    }
}

fun showUsers() {
    for (user in UsersCache.getUsers()) {
        println(user)
    }
}