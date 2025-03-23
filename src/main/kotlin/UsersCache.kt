object UsersCache {
    private var users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUsers(): List<User> {
        return users
    }

    fun getUser(username: String): User? {
        for (user in users) {
            if (user.username == username)
                return user
        }
        return null
    }

    fun searchByUsername(filter: String): List<User> {
        val matchedUsers = mutableListOf<User>()

        for (user in users) {
            if (user.username.lowercase().contains(filter.lowercase()))
                matchedUsers.add(user)
        }
        return matchedUsers
    }

    fun searchByRepo(filter: String): List<String> {
        val matchedRepos = mutableListOf<String>()

        for (user in users) {
            for (repo in user.publicRepos) {
                if (repo.lowercase().contains(filter.lowercase()))
                    matchedRepos.add("${user.username} | $repo")
            }
        }
        return matchedRepos
    }
}