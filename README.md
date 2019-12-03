# Password Manager
> Fall 2019, CS 3443 - Application Programming

### Building & Running
<<<<<<< HEAD
1. Requires Java 8
<<<<<<< HEAD
2. Gradle is required to fetch dependencies. 
3. Clone the repo and run: `./gradlew run`  
	- **If using Eclipse:** Run `./gradlew eclipse` or `init.bat` _before_ importing the project, 
	or you're going to have a bad time.  
	- **If using Intellij:** `./gradlew idea`
=======
2. Gradle is required to fetch dependencies. (Included in this repo)
3. Clone the repo and run `init.bat` on Windows, or `init.sh` on Linux.  
4. Import the project into Eclipse, or run `run.bat` or `run.sh`.
5. The password to the demo login is `demo`

### Features
- Simple to use interface.
- Search function.
- Edit or delete existing entries.
- CSV Export

### Features Not Implemented
- Automatic password hiding/showing
- Inactivity logout
>>>>>>> develop

=======
1. Requires Java 8 (version used on VDI)
	- Gradle is required to fetch dependencies. (Wrapper is included in this repo)
2. Clone the repo and run `init.bat` on Windows, or `init.sh` on Linux.  
3. Import the project into Eclipse, or run `run.bat` or `run.sh` to run without importing into Eclipse.
4. The application will be setup in demo mode. 
    - **The password is `demo`**

### Features
- Simple to use interface. 
- Search through entries by title.
- Add new, and edit or delete existing entries.
- Export everything to a CSV file.
- Random password generator.

### Issues & Features Not Implemented
- Automatic password hiding/showing. Currently it defaults to hidden.
- Inactivity logout.
- macOS has some element sizing issues, but otherwise still functional.
>>>>>>> develop
