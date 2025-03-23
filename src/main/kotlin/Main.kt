import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import kotlin.system.exitProcess

fun main(): Unit = runBlocking {
    while (true) {
        println("1. Add User\n2. Show Users\n3. Search by Username\n4. Search by Repo\n5. Exit")
        val choice = readLine()
        when (choice) {
            "1" -> addUser()
            "2" -> showUsers()
            "3" -> searchByUsername()
            "4" -> searchByRepo()
            "5" -> exitProcess(0)
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

fun searchByUsername() {
    println("Enter username:")
    val username = readLine() ?: ""

    val foundUsers = UsersCache.searchByUsername(username)
    if (foundUsers.isNotEmpty()) {
        for (user in foundUsers)
            println(user)
        return
    }
    println("No users found")
}

fun searchByRepo() {
    println("Enter repository name:")
    val repoName = readLine() ?: ""

    val foundRepos = UsersCache.searchByRepo(repoName)
    if (foundRepos.isNotEmpty()) {
        for (repo in foundRepos)
            println(repo)
        return
    }
    println("No repositories found")
}