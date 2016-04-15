[![Build Status](https://travis-ci.org/Serneum/TimeTracker.svg?branch=master)](https://travis-ci.org/Serneum/TimeTracker)

### Functionality
- View/Create/Edit customers
- View/Create/Edit projects
- View/Create/Edit tasks
- View/Create/Edit time entries
- Log in
- Log out
- Redirect to login page when not authenticated

### How to use
Assuming you have Tomcat set up in /Library/Tomcat, just run
```
gradle deploy
```
Then, start Tomcat and point your browser to `http://localhost:8080/entries`
