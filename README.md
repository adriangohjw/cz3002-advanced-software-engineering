# cz3002-advanced-software-engineering
NTU Computer Science - CZ3002 Advanced Software Engineering

# Setting up

## Setting up local environment (Windows)

This is a very simplified guide on setting up local environment for Windows

### Setting up repository

1. Go to a folder where you want the repository to be in
2. Clone the repository
`git clone https://github.com/adriangohjw/cz3002-advanced-software-engineering.git`

### Setting up PostgreSQL

PostgreSQL is the relational DBMS of choice in this project
 
1. Download and install PostgreSQL from [Windows installers](https://www.postgresql.org/download/windows/)
 2. Use the following credentials during the installation (otherwise you can update the Config file):
	 1. Username = `postgres` and password = `password`
 3. Open the psql command-line tool by 
	 1. In the Windows CMD, run the command: `psql -U postgres`  
	 2. Enter password when prompted
	 3. Run the command: `create database "cz3002";`
    
Reference: [Set Up a PostgreSQL Database on Windows](https://www.microfocus.com/documentation/idol/IDOL_12_0/MediaServer/Guides/html/English/Content/Getting_Started/Configure/_TRN_Set_up_PostgreSQL.htm)

# For contributors

## Git

Refer to this [simplified guide](https://rogerdudler.github.io/git-guide/) on how Git works

The reasons there's a need for us to standardize on these processes and naming conventions, are to improve the team workflow and efficiencies.

Tip: I suggest using Visual Studio Code for the git shortcuts and friendly user interfaces

### When starting on a new change
1. Create a new branch using the command `git checkout -b aaa/bbb/branch-name`
    - `aaa` refers to the "team" in which the work is under e.g. `backend`, `mobile`, `qa`
    - `bbb` refers to the type of changes
      - `feature` for a starting an entire new feature from scratch
      - `enhancement` for making upgrades to an existing feature
      - `fix` for fixing bugs
    - `branch-name` is a naming for the new thing you are working on
    - E.g. For adding data models on the backend, branch name will be `backend/feature/add-data-model`
2. Add whatever changes you made using `git add xxx` and `git commit -m "message"` (Refer to the guide)
   - Please use meaningful commit messages instead of generic ones
   - Example of bad commit message: `Fix bug`
   - Example of better commit message: `Fix bug from not handling exception in UsersController`
3. Push to remote branch using the command `git push origin aaa/bbb/branch-name`
4. Create a pull request (PR) and add those who need to be aware of these changes as the PR reviewer(s).
