package it.sad.students.eventboard.controller.api;


import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.Report;
import it.sad.students.eventboard.persistenza.model.ReportType;
import it.sad.students.eventboard.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin ("http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    private ReportService reportService;

    @PostMapping("/comment/{id_comment}")
    public ResponseEntity reportComment(@PathVariable Long id_comment, @RequestBody Report report,@RequestHeader (name="Authorization") String token){
        //codice 403 se non si è loggati
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(id_comment,null,report,"comment",token);
    }

    @PostMapping("/event/{id_event}")
    public ResponseEntity reportEvent(@PathVariable Long id_event, @RequestBody Report report,@RequestHeader (name="Authorization") String token){
        //codice 403 se non si è loggati
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(id_event,null,report,"event",token);
    }

    @PostMapping("/bug")
    public ResponseEntity reportBug(@RequestBody Report report,@RequestHeader (name="Authorization") String token){
        //codice 403 se non si è loggati
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(null,null,report,"bug",token);
    }

    @PostMapping("/review/{id_event}/{id_person}")
    public ResponseEntity reportReview(@PathVariable Long id_event, @PathVariable Long id_person, @RequestBody Report report,@RequestHeader (name="Authorization") String token){
        //codice 403 se non si è loggati
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(id_event,id_person,report,"review",token);
    }

    @RequestMapping(value = "/admin/close/{id_report}",method = RequestMethod.PUT)
    public ResponseEntity closeReport(@PathVariable Long id_report,@RequestHeader (name="Authorization") String token){
        //codice 403 se non si è admin
        //400 id inserito nullo o errore nella chiusura
        //404 id non trotato
        //200 ok
        return reportService.closeReport(id_report,token);

    }

    @RequestMapping("/admin/all")
    public ResponseEntity<Iterable<Report>> getReports(@RequestHeader (name="Authorization") String token){
        //codice 403 se non si è admin
        //restituisce sempre 200 , e lista dei report presenti
        return reportService.getReports(token);
    }

    @RequestMapping("/admin/{id_rep}")
    public ResponseEntity<Report> getReport(@PathVariable Long id_rep,@RequestHeader (name="Authorization") String token){
        //codice 403 se non si è admin
        //id nullo errore 400
        //ok 200, e restituisce il report
        //404 id non trovato
        return reportService.getReport(id_rep,token);
    }

    @RequestMapping("/types")
    public ResponseEntity<ReportType[]> getEventType(){
        return reportService.getReportType();
    }

}
