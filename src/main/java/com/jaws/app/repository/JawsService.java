package com.jaws.app.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.jaws.app.dao.JawsDao;
import com.jaws.app.model.EventModel;
import com.jaws.app.model.JawsModel;
import com.jaws.app.util.PdfGeneratorUtil;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JawsService {

    private final JawsDao jawsDao;
    private final PdfGeneratorUtil pdfGeneratorUtil;

    public List<JawsModel> getByMembershipNo(String membershipNo) {
        return jawsDao.findByMembershipNo(membershipNo);
    }

    public List<JawsModel> getByName(String name) {
        return jawsDao.findByName(name);
    }

    public List<JawsModel> getByMobileNo(String mobileNo) {
        return jawsDao.findByMobileNo(mobileNo);
    }

    public List<EventModel> getAllEvents() {
        return jawsDao.findAllEvents();
    }

    public void updatePhoto(String membershipNo, byte[] photo) {
        jawsDao.updatePhotoByMembershipNo(membershipNo, photo);
    }

    public void updatePhotoByMobileNo(String mobileNo, byte[] photo) {
        jawsDao.updatePhotoByMobileNo(mobileNo, photo);
    }

    public void storeAttendance(Integer eventId, Integer uniqueId) {
        jawsDao.saveAttendance(eventId, uniqueId);
    }

    public byte[] generateAttendancePdf(Long eventId) {

        List<Map<String, Object>> data = jawsDao.findEventAttendance(eventId);
        String eventName = jawsDao.findEventNameById(eventId);
            return pdfGeneratorUtil.generateAttendancePdf(data, eventId, eventName);
    } 
}