package com.jaws.app.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jaws.app.model.EventModel;
import com.jaws.app.model.JawsModel;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JawsDao {

    private final JdbcTemplate jdbcTemplate;
    
    private final JawsModelRowMapper rowMapper;

    @Value("${queryByMembershipNo}")
    private String queryByMembershipNo;

    @Value("${queryByName}")
    private String queryByName;

    @Value("${queryByMobileNo}")
    private String queryByMobileNo;

    @Value("${queryEventList}")
    private String queryEventList;

    @Value("${queryDownloadPDF}")
    private String queryDownloadPDF;

    @Value("${queryInsertAttendance}")
    private String queryInsertAttendance;

    @Value("${queryGetEventName}")
    private String queryGetEventName;

    @Value("${updatePhotoByMembershipNo}")
    private String updatePhotoByMembershipNo;

    @Value("${updatePhotoByMobileNo}")
    private String updatePhotoByMobileNo;


    public List<JawsModel> findByMembershipNo(String membershipNo) {
        return jdbcTemplate.query(queryByMembershipNo, rowMapper, membershipNo);
    }

    public List<JawsModel> findByMobileNo(String mobileNo) {
        return jdbcTemplate.query(queryByMobileNo, rowMapper, mobileNo);
    }

    public List<JawsModel> findByName(String name) {
        String searchPattern = "%" + name + "%";
        return jdbcTemplate.query(queryByName, rowMapper, searchPattern);
    }

    public List<EventModel> findAllEvents() {
        return jdbcTemplate.query(queryEventList, new EventModelRowMapper());
    }

    public void saveAttendance(Integer eventId, Integer memberUniqueId) {
        try {
            jdbcTemplate.update(queryInsertAttendance, eventId, memberUniqueId);
            log.info("Attendance stored for eventId={}, memberUniqueId={}", eventId, memberUniqueId);
        } catch (DuplicateKeyException e) {
            log.info("Due to duplicate key error, attendance not stored for eventId={}, memberUniqueId={}", eventId, memberUniqueId);
        } 
        catch (Exception e) {
            log.error("Error storing attendance for eventId={}, memberUniqueId={}", eventId, memberUniqueId, e.getMessage());
        }
    }

    public List<Map<String, Object>> findEventAttendance(Long eventId) {
        return jdbcTemplate.queryForList(queryDownloadPDF, eventId);
    }

    public String findEventNameById(Long eventId) {
        return jdbcTemplate.queryForObject(queryGetEventName, String.class, eventId);
    }

    public void updatePhotoByMembershipNo(String membershipNo, byte[] photo) {
        jdbcTemplate.update(updatePhotoByMembershipNo, photo, membershipNo);
    }

    public void updatePhotoByMobileNo(String mobileNo, byte[] photo) {
        jdbcTemplate.update(updatePhotoByMobileNo, photo, mobileNo);
    }
}