import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

fun main(): Unit = runBlocking {
    while (true) {
        println("-----\nMenu\n-----")
        println("1. Add User\n2. Show Users\n3. Search by Username\n4. Search by Repo\n5. Exit")
        print("Enter command number: ")
        val choice = readLine()
        when (choice) {
            "1" -> addUser()
            "2" -> showUsers()
            "3" -> searchByUsername()
            "4" -> searchByRepo()
            "5" -> exitProcess(0)
            else -> println("Invalid command!")
        }
    }
}

suspend fun addUser() {
    print("Enter username: ")
    val username: String = readLine() ?: ""

    var user = UsersCache.getUser(username)
    if (user != null) {
        println("User already exists!")
        println(user)
        return
    }

    try {
        val githubUser: GithubUser = RetrofitClient.github.getUser(username)
        val followersCount = RetrofitClient.github.getUserFollowers(username).count()
        val followingsCount = RetrofitClient.github.getUserFollowings(username).count()
        val repos = RetrofitClient.github.getUserRepos(username)
        val repoNames = repos.map { it.repoName }
        user = User(
            githubUser.username, followersCount, followingsCount,
            convertTime(githubUser.createdAt), repoNames
        )
        UsersCache.addUser(user)
        println("$username added successfully:")
        println(user)
    } catch (e: HttpException) {
        if (e.code() == 404)
            println("User not found!")
        else
            println("An error occurred!")
    }
}

fun showUsers() {
    val users = UsersCache.getUsers()
    if (users.isNotEmpty()) {
        for (user in UsersCache.getUsers()) {
            println(user)
        }
        return
    }
    println("No users have been added yet!")
}

fun searchByUsername() {
    print("Enter username: ")
    val username = readLine() ?: ""

    val foundUsers = UsersCache.searchByUsername(username)
    if (foundUsers.isNotEmpty()) {
        for (user in foundUsers)
            println(user)
        return
    }
    println("No users found!")
}

fun searchByRepo() {
    print("Enter repository name: ")
    val repoName = readLine() ?: ""

    val foundRepos = UsersCache.searchByRepo(repoName)
    if (foundRepos.isNotEmpty()) {
        for (repo in foundRepos)
            println(repo)
        return
    }
    println("No repositories found!")
}

fun convertTime(utcTime: String): String {
    val iranTime = Instant.parse(utcTime)
        .atZone(ZoneId.of("Asia/Tehran"))
        .toLocalDateTime()

    return iranTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}