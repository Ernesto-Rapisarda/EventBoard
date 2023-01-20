package it.sad.students.eventboard.service;

import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.Report;
import it.sad.students.eventboard.persistenza.model.ReportType;
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
    private final AuthorizationControll authorizationControll;


    public ResponseEntity addReport(Long id,Long person, Report report, String type,String token) {
        try{
            if(report==null)
                return statusCodes.commandError();//400

            if(!authorizationControll.checkOwnerAuthorization(report.getPerson(),token))
                return statusCodes.unauthorized();

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
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    public ResponseEntity closeReport(Long id_report,String token) {
        try{
            if(id_report==null)
                return statusCodes.commandError();//400

            if(!authorizationControll.checkAdminAuthorization(token))
                return statusCodes.unauthorized();

            Report report=DBManager.getInstance().getReportDao().findByPrimaryKey(id_report);
            if(report==null)
                return statusCodes.notFound();//404
            report.setStatus(false);
            if(DBManager.getInstance().getReportDao().saveOrUpdate(report))
                return statusCodes.ok();//200
            else
                return statusCodes.commandError();//400
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    // TODO: 18/01/2023 gestire errori
    public ResponseEntity<Iterable<Report>> getReports(String token) {
        if(!authorizationControll.checkAdminAuthorization(token))
            return statusCodes.unauthorized();
        return statusCodes.okGetElements(DBManager.getInstance().getReportDao().findAll());
    }

    public ResponseEntity<Report> getReport(Long id,String token) {
        try{
            if (id==null)
                return statusCodes.commandError();//400

            if(!authorizationControll.checkAdminAuthorization(token))
                return statusCodes.unauthorized();

            Report report = DBManager.getInstance().getReportDao().findByPrimaryKey(id);
            if(report!=null)
                return statusCodes.okGetElement(report);
            else
                return statusCodes.notFound();
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }

    }

    public ResponseEntity<ReportType[]> getReportType(){
        return ResponseEntity.ok(ReportType.values());
    }

}
