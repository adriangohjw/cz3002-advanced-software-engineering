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

## Setting up local environment (Linux / WSL)

This is a very simplified guide on setting up local environment for Linux / Windows Subsystem for Linux (WSL)

### Setting up repository

1. Go to a folder where you want the repository to be in
2. Clone the repository
`git clone https://github.com/adriangohjw/cz3002-advanced-software-engineering.git`

### Setting up PostgreSQL

PostgreSQL is the relational DBMS of choice in this project
 
1. Download and install PostgreSQL from [PostgreSQL website](https://www.postgresql.org/download/linux/ubuntu/)
 2. Use the following credentials during the installation (otherwise you can update the Config file):
	 1. Username = `postgres` and password = `password`
 3. Open the psql command-line tool by 
	 1. In the Windows CMD, run the command: `psql -U postgres`  
	 2. Enter password when prompted
	 3. Run the command: `create database "cz3002";`

### Setting up Ruby on Rails

1. Refer to guide on [setting up Ruby on Rails with Rbenv on Ubuntu](https://www.digitalocean.com/community/tutorials/how-to-install-ruby-on-rails-with-rbenv-on-ubuntu-18-04)
2. Go to the folder `/scansmart-api` and run the command `bundle install` to install gem dependencies 
   - You should see something along the line of `Bundle complete! 20 Gemfile dependencies, 72 gems now installed.`

## Setting up Stripe test account

1. Create a new stripe account [here](https://dashboard.stripe.com/register)
2. Add your Publishable key and Secret key to your environment

# Running the application

### DB migrations and population with test data using Faker gem

1. Run the command `rails db:migrate` --> This set up your database with tables etc.
2. Run the command `rails db:reset` --> This populates your database

### Running the server

1. Run the command `rails s` and you should see something along the line to the following
   ```
   => Booting Puma
   => Rails 5.2.4.3 application starting in development 
   => Run `rails server -h` for more startup options
   Puma starting in single mode...
   * Version 3.12.6 (ruby 2.5.1-p57), codename: Llamas in Pajamas
   * Min threads: 5, max threads: 5
   * Environment: development
   * Listening on tcp://localhost:3000
   Use Ctrl-C to stop
   ```

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
