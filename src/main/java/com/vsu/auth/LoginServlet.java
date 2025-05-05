package com.vsu.auth;

import com.vsu.Entity.Profile;
import com.vsu.repository.ProfileRepository;
import com.vsu.repository.RecordRepository;
import com.vsu.service.ProfileService;
import com.vsu.utils.URLUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    private final ProfileService profileService = new ProfileService(new ProfileRepository(),new RecordRepository());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (profileService.doLogin(login, password)) {
            HttpSession session = req.getSession();
            Profile profile = (Profile) profileService.getProfileByLogin(login);
            session.setAttribute("profile", profile);
            session.setAttribute("userId", profile.getId());
            resp.sendRedirect(URLUtils.getFullUrlForRedirect(req, "/user/user"));
        } else {
            req.setAttribute("errorMessage", "Неправильный логин или пароль");
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/auth/login.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/auth/login.jsp");
        rd.forward(req, resp);
    }
}