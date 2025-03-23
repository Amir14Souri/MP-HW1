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
}