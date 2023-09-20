# session-server
Using hibernate.Session to work with db (save, get, delete)

Primary functions:
----
- 'Sign up' - 
Simply created by addressing to DB and if there is no such login, then new user is created
- 'Sign in' -
Implemented by getting sessionId from HttpServletRequest and then invoking method 'saveSessionID' in SessionDAO.

Stack:
- Jetty
- Hibernate
- Postgresql
- Gson
