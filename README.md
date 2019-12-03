# Password Manager
> Fall 2019, CS 3443 - Application Programming

### Building & Running
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
