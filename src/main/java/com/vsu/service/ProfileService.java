package com.vsu.service;

import com.vsu.Entity.Profile;
import com.vsu.Entity.Record;
import com.vsu.exception.DBException;
import com.vsu.filter.LoginFilter;
import com.vsu.repository.ProfileRepository;
import com.vsu.repository.RecordRepository;
import com.vsu.utils.CryptoUtils;
import com.vsu.utils.PasswordHasher;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileService {
    private final ProfileRepository profileRepository;
    private final RecordRepository recordRepository;

    private static Logger LOGGER = Logger.getLogger(ProfileService.class.getName());

    public ProfileService(ProfileRepository profileRepository, RecordRepository recordRepository) {
        this.profileRepository = profileRepository;
        this.recordRepository = recordRepository;
    }

    public boolean doLogin(String login, String password) {
        Profile profile = profileRepository.findByLogin(login);
        if (profile == null) return false;

        return PasswordHasher.verifyPassword(
                password,
                profile.getSalt(),
                profile.getPassword()
        );
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() > 6;
    }

    public boolean registerUser(String login, String password) {
        if (!isValidLogin(login)) {
            return false;
        }

        if (!isValidPassword(password)) {
            return false;
        }

        String salt = PasswordHasher.generateSalt();
        String hashedPassword = PasswordHasher.hashPassword(password, salt);

        Profile profile = new Profile();
        profile.setLogin(login);
        profile.setPassword(hashedPassword);
        profile.setSalt(salt);

        try {
            return profileRepository.save(profile);
        } catch (DBException e) {
            LOGGER.log(Level.INFO, "Ошибка при регистрации пользователя: " + e.getMessage());
            return false;
        }
    }

    public boolean isValidLogin(String login) {
        if (login == null || login.length() <= 4) {
            return false;
        }

        try {
            return profileRepository.findByLogin(login) == null;
        } catch (DBException e) {
            LOGGER.log(Level.INFO, "Ошибка при проверке логина: " + e.getMessage());
            return false;
        }
    }

    public Profile getProfileByLogin(String login) {
        return profileRepository.findByLogin(login);
    }
    public boolean addRecord(Long profileId, String siteAddress, String login, String password) {
        try {
            Profile profile = profileRepository.findById(profileId);
            if (profile == null) return false;

            String encrypted = CryptoUtils.encrypt(password, profile.getSalt());

            Record record = new Record();
            record.setProfileId(profileId);
            record.setSiteAddress(siteAddress);
            record.setLogin(login);
            record.setPassword(encrypted);

            return recordRepository.save(record);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Record> getAllRecords(Long profileId) {
        return recordRepository.getRecordsByProfileId(profileId);
    }

    public String getDecryptedPassword(Long recordId) {
        try {
            Record record = recordRepository.findById(recordId);
            if (record == null) return null;

            Profile profile = profileRepository.findById(record.getProfileId());
            return CryptoUtils.decrypt(record.getPassword(), profile.getSalt());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateRecord(Long recordId, String siteAddress, String login, String password) throws Exception {
        Record record = recordRepository.findById(recordId);
        if (record == null) return false;

        record.setSiteAddress(siteAddress);
        record.setLogin(login);

        if (password != null && !password.isEmpty()) {
            Profile profile = profileRepository.findById(record.getProfileId());
            String encrypted = CryptoUtils.encrypt(password, profile.getSalt());
            record.setPassword(encrypted);
        }


        return recordRepository.update(record);
    }

    public boolean deleteRecord(Long recordId) {
        return recordRepository.delete(recordId);
    }
}