object UsersCache {
    private var users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }
}