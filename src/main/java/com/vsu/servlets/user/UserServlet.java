package com.vsu.servlets.user;

import com.vsu.Entity.Profile;
import com.vsu.Entity.Record;
import com.vsu.repository.ProfileRepository;
import com.vsu.repository.RecordRepository;
import com.vsu.service.ProfileService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/user/user")
public class UserServlet extends HttpServlet {
    private final ProfileRepository profileRepository = new ProfileRepository();
    private final RecordRepository recordRepository = new RecordRepository();
    private final ProfileService profileService = new ProfileService(profileRepository, recordRepository);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Profile profile = (Profile) session.getAttribute("profile");

        // Получаем параметр поиска
        String searchQuery = req.getParameter("search");

        List<Record> allRecords = profileService.getAllRecords(profile.getId());
        List<Record> recordsToShow;

        // Фильтрация записей по поисковому запросу
        if (searchQuery != null && !searchQuery.isEmpty()) {
            String searchLower = searchQuery.toLowerCase();
            recordsToShow = allRecords.stream()
                    .filter(record ->
                            record.getSiteAddress().toLowerCase().contains(searchLower) ||
                                    record.getLogin().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        } else {
            recordsToShow = allRecords; // Показываем все записи, если поисковый запрос пуст
        }

        Map<Record, String> recordPasswords = new LinkedHashMap<>();
        for (Record record : recordsToShow) {
            String decryptedPassword = profileService.getDecryptedPassword(record.getId());
            recordPasswords.put(record, decryptedPassword);
        }

        req.setAttribute("profileName", profile.getLogin());
        req.setAttribute("recordPasswords", recordPasswords);
        req.setAttribute("searchQuery", searchQuery); // Передаем обратно в форму для отображения

        req.getRequestDispatcher("/WEB-INF/user/user.jsp").forward(req, resp);
    }
}