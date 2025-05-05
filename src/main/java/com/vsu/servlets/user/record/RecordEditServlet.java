package com.vsu.servlets.user.record;

import com.vsu.Entity.Record;
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

@WebServlet("/user/record/edit")
public class RecordEditServlet extends HttpServlet {
    private final ProfileRepository profileRepository = new ProfileRepository();
    private final RecordRepository recordRepository = new RecordRepository();
    private final ProfileService profileService = new ProfileService( profileRepository,recordRepository);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long recordId = Long.parseLong(req.getParameter("id"));
        Record record = new RecordRepository().findById(recordId);

        if (record != null) {
            req.setAttribute("record", record);
            req.setAttribute("passwordDecrypted", (profileService.getDecryptedPassword(recordId)));
            req.getRequestDispatcher("/WEB-INF/user/record.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long recordId = Long.parseLong(req.getParameter("id"));
        String siteAddress = req.getParameter("siteAddress");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            if (profileService.updateRecord(recordId, siteAddress, login, password)) {
                resp.sendRedirect(URLUtils.getFullUrlForRedirect(req, "/user/user"));
            } else {
                req.setAttribute("error", "Ошибка изменения записи");
                req.getRequestDispatcher("/WEB-INF/user/record.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
