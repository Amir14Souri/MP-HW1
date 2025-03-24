# GitHub User Info Fetcher

A Kotlin-based terminal application that fetches GitHub user information using the GitHub REST API. It caches retrieved data in memory to prevent redundant API calls, improving efficiency and reducing network load.

## Features
The app has these features:
- Fetch GitHub user information by username, including:
  - Username
  - Number of followers
  - Number of following
  - Account creation date (Iran standard time)
  - List of public repositories
- Display a list of stored users
- Search users by username from stored data
- Search repositories by name from stored data
- Exit the application

## Technical Details
- Uses [GitHub REST API](https://docs.github.com/en/rest?apiVersion=2022-11-28#get-a-user)
- Stores fetched data in memory
- Handles API errors robustly (such as 404 for not found users)
- Implements Kotlin design patterns for maintainability and scalability
- Uses the following libraries:
  - [Retrofit](https://square.github.io/retrofit) for API communication
  - [Gson](https://github.com/google/gson) for JSON parsing

## Installation & Usage
### Prerequisites
- Make sure to have JDK (Java Development Kit) installed. Preferred version is 23.
- A GitHub API token may be required for higher rate limits.

### Clone the repository:
```sh
git clone https://github.com/Amir14Souri/MP-HW1.git
cd MP-HW1
```

### Build and Run:
You can use IntelliJ Idea to run the project easily, but if you don't have it installed, instructions are as follows:

#### Windows:
make sure to have the java added to system environment variables, then run:
```sh
./gradlew.bat run
```

#### macOS & Linux:
```sh
chmod +x gradlew
./gradlew run
```

## Menu Options
1. Add User
2. Show Users
3. Search by Username
4. Search by Repo
5. Exit
