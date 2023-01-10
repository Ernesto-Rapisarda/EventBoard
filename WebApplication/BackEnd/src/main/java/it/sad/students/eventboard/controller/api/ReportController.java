package it.sad.students.eventboard.controller.api;


import it.sad.students.eventboard.persistenza.model.Report;
import it.sad.students.eventboard.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    private ReportService reportService;


    @PostMapping("/comment/{id_comment}")
    public ResponseEntity reportComment(@PathVariable Long id_comment, @RequestBody Report report){
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(id_comment,null,report,"comment");
    }

    @PostMapping("/event/{id_event}")
    public ResponseEntity reportEvent(@PathVariable Long id_event, @RequestBody Report report){
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(id_event,null,report,"event");
    }

    @PostMapping("/bug")
    public ResponseEntity reportBug(@RequestBody Report report){
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(null,null,report,"bug");
    }

    @PostMapping("/review/{id_event}/{id_person}")
    public ResponseEntity reportReview(@PathVariable Long id_event, @PathVariable Long id_person, @RequestBody Report report){
        //400 report vuoto o errore di salvataggio nel db o nell'invio della email
        //404 evento,commento,review non trovato
        //200 ok
        return reportService.addReport(id_event,id_person,report,"review");
    }

    @RequestMapping(value = "/close/{id_report}",method = RequestMethod.PUT)
    public ResponseEntity closeReport(@PathVariable Long id_report){
        //400 id inserito nullo o errore nella chiusura
        //404 id non trotato
        //200 ok
        return reportService.closeReport(id_report);

    }

    @RequestMapping("/all")
    public ResponseEntity<Iterable<Report>> getReports(){
        return reportService.getReports();
    }

    @RequestMapping("/{id_rep}")
    public ResponseEntity<Report> getReport(@PathVariable Long id){
        return null;
    }

}
