package servlets;


import com.google.gson.Gson;
import dataSets.UsersDataSet;
import service.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
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

        if (accountService.getUser(login) == null) {
            accountService.saveToDB(login, password);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("This login is already in use");
            return;
        }

        UsersDataSet dataSet = new UsersDataSet(login, password);
        response.setStatus(HttpServletResponse.SC_OK);
        String json = new Gson().toJson(dataSet);
        response.getWriter().println(json);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UsersDataSet dataSet = accountService.getUser(login);
        response.setContentType("text/html;charset=utf-8");

        if (login == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (dataSet == null || !dataSet.getPassword().equals(password)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.deleteProfile(dataSet);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Account has been deleted");
    }
}