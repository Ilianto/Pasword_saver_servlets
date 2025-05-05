package com.vsu.servlets.user.record;

import com.vsu.repository.ProfileRepository;
import com.vsu.repository.RecordRepository;
import com.vsu.service.ProfileService;
import com.vsu.utils.URLUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/record/add")
public class RecordAddServlet extends HttpServlet {

    ProfileService profileService = new ProfileService(new ProfileRepository(), new RecordRepository());

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/record.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        HttpSession session = req.getSession(false);
        Long profileId = (Long) session.getAttribute("userId");

        String siteAddress = req.getParameter("siteAddress");
        String login = req.getParameter("login");
        String password = req.getParameter("password");


        if (profileService.addRecord(profileId, siteAddress, login, password)) {
            resp.sendRedirect(URLUtils.getFullUrlForRedirect(req, "/user/user"));
        } else {
            req.setAttribute("error", "Ошибка добавления записи");
            req.getRequestDispatcher("/WEB-INF/user/record.jsp").forward(req, resp);
        }
    }
}