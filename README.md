# cz3002-advanced-software-engineering
NTU Computer Science - CZ3002 Advanced Software Engineering


---

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
