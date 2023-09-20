# session-server
Using hibernate.Session to work with db (save, get, delete)

Primary functions:
----
- 'Sign up' - 
simply builded by addressing to DB and if there is no such login, then new user is created
- 'Sign in' -
implemented by getting sessionId from HttpServletRequest and then invoking of method 'saveSessionID' in SessionDAO.

Stack:
- Jetty
- Hibernate
- Postgresql
- Gson
