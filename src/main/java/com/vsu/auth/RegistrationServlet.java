package com.vsu.auth;

import com.vsu.Entity.Profile;
import com.vsu.exception.DBException;
import com.vsu.repository.ConnectionFactory;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/auth/registration")
public class RegistrationServlet extends HttpServlet {
    private final ProfileService profileService = new ProfileService(new ProfileRepository(),new RecordRepository());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordConfirmation = req.getParameter("passwordConfirmation");

        // Проверка совпадения паролей
        if (!password.equals(passwordConfirmation)) {
            req.setAttribute("error", "Пароли не совпадают");
            req.getRequestDispatcher("/WEB-INF/auth/registration.jsp").forward(req, resp);
            return;
        }

        try {
            if (profileService.registerUser(login, password)) {
                HttpSession session = req.getSession();
                Profile profile = profileService.getProfileByLogin(login);
                session.setAttribute("profile", profile);
                session.setAttribute("userId", profile.getId());
                resp.sendRedirect(URLUtils.getFullUrlForRedirect(req, "/user/user"));
            } else {
                req.setAttribute("error", "Логин уже занят или не соответствует требованиям");
                req.getRequestDispatcher("/WEB-INF/auth/registration.jsp").forward(req, resp);
            }
        } catch (DBException e) {
            req.setAttribute("error", "Ошибка базы данных при регистрации");
            req.getRequestDispatcher("/WEB-INF/auth/registration.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/auth/registration.jsp").forward(req, resp);
    }
}