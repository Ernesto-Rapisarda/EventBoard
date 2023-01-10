package it.sad.students.eventboard.service;

import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Report;
import it.sad.students.eventboard.service.httpbody.StatusCodes;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReportService {
    private EmailSenderService emailSenderService;
    private StatusCodes statusCodes;


    public ResponseEntity addReport(Long id,Long person, Report report, String type) {
        if(report==null)
            return statusCodes.commandError();//400

        report.setDate(LocalDateTime.now());

        if(type.equals("event") && DBManager.getInstance().getEventDao().findByPrimaryKey(id)==null)
            return statusCodes.notFound();//404
        else if(type.equals("comment") && DBManager.getInstance().getCommentDao().findByPrimaryKey(id)==null)
            return statusCodes.notFound();//404
        else if (type.equals("review") && DBManager.getInstance().getReviewDao().findByPrimaryKey(person,id)==null)
            return statusCodes.notFound();//404


        if(DBManager.getInstance().getReportDao().saveOrUpdate(report) && emailSenderService.sendReport(id,person, report,type))
            return statusCodes.ok();//200
        else
            return statusCodes.commandError();//400

    }

    public ResponseEntity closeReport(Long id_report) {
        if(id_report==null)
            return statusCodes.commandError();//400

        Report report=DBManager.getInstance().getReportDao().findByPrimaryKey(id_report);
        if(report==null)
            return statusCodes.notFound();//404
        report.setStatus(false);
        if(DBManager.getInstance().getReportDao().saveOrUpdate(report))
            return statusCodes.ok();//200
        else
            return statusCodes.commandError();//400
    }
}
