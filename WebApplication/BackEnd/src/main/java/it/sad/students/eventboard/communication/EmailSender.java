package it.sad.students.eventboard.communication;


import it.sad.students.eventboard.persistenza.model.Report;

public interface EmailSender {
    boolean sendEmail(EmailMessage emailMessage);
    boolean sendReport(Long id,Long Person, Report report, String type);

}
