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

import java.io.IOException;
@WebServlet("/user/record/delete")
public class RecordDeleteServlet extends HttpServlet {
    ProfileService profileService = new ProfileService(new ProfileRepository(), new RecordRepository());
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long recordId = Long.parseLong(req.getParameter("id"));

        if (profileService.deleteRecord(recordId)) {
            resp.sendRedirect(URLUtils.getFullUrlForRedirect(req,"/user/user"));
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка удаления записи");
        }
    }
}