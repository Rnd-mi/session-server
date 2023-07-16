package servlets;

import dataSets.SessionDataSet;
import dataSets.UsersDataSet;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();

        SessionDataSet dataSet = accountService.getUserBySessionID(sessionId);
        response.setContentType("text/html;charset=utf-8");

        if (dataSet == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("You need to sign in firstly");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("You are currently logged in \n login: " + dataSet.getLogin());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");

        if (login == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsersDataSet dataSet = accountService.getUser(login);
        if (dataSet == null || !dataSet.getPassword().equals(password)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Login or password are not true");
            return;
        }

        accountService.addSession(request.getSession().getId(), dataSet);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("You have successfully logged in \n login: " + login);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionID = request.getSession().getId();
        response.setContentType("text/html;charset=utf-8");
        String login = accountService.getUserBySessionID(sessionID).getLogin();

        if (login == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("You need to sign in firstly");
        } else {
            accountService.deleteSession(sessionID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Goodbye... See you later!");
        }
    }
}