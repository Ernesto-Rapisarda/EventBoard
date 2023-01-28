package it.sad.students.eventboard.communication;


import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Report;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;




@Service
public class EmailSenderService implements EmailSender {


    JavaMailSender emailSender;

    public EmailSenderService(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    @Override
    public boolean sendEmail(EmailMessage emailMessage) {
        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("progetto.webapp.2023@gmail.com");
            simpleMailMessage.setTo(emailMessage.getTo());
            simpleMailMessage.setSubject(emailMessage.getSubject());
            simpleMailMessage.setText(emailMessage.getMessage());

            this.emailSender.send(simpleMailMessage);
            return true;

        }catch (MailException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendReport(Long id,Long person, Report report,String type) {
        try {
            SimpleMailMessage userMessage = new SimpleMailMessage();
            SimpleMailMessage adminMessage = new SimpleMailMessage();
            userMessage.setFrom("progetto.webapp.2023@gmail.com");

            userMessage.setTo(DBManager.getInstance().getPersonDao().findByPrimaryKey(report.getPerson()).getEmail());
            userMessage.setSubject("Conferma report del "+type);
            userMessage.setText(userMessage());
            this.emailSender.send(userMessage);


            adminMessage.setFrom("progetto.webapp.2023@gmail.com");
            adminMessage.setTo("progetto.webapp.2023@gmail.com");
            if(type.equals("bug"))
                adminMessage.setSubject("Segnalazione "+type );
            else
                adminMessage.setSubject("Segnalazione "+type+" " +id.toString());
            if (type.equals("comment")){
                adminMessage.setText(adminMessage("commento",report,id.toString()));
            }
            else if(type.equals("event"))
                adminMessage.setText(adminMessage("evento",report,id.toString()));
            else if(type.equals("review"))
                adminMessage.setText(adminMessage("review",report,("Evento: "+id+" User: "+ person)));

            else{
                adminMessage.setText(adminMessage("bug",report,"no id"));
            }




            this.emailSender.send(adminMessage);
            return true;


        }catch (MailException e){
            e.printStackTrace();
            return false;
        }
    }

    private String adminMessage(String tipo,Report report,String id){
        return ( "L'utente "+report.getPerson()+" sta segnalando "+ tipo +"id: "+ id+"\n"+
                "Dati report:\n"+
                "\tId: "+report.getId()+"\n"+
                "\tDate: "+report.getDate()+"\n"+
                "\tMessage: "+report.getMessage()+"\n"+
                "\tTipo: "+report.getType());

    }

    private String userMessage(){
        return (
        "Grazie per averci inviato la segnalazione,\n\n"+
                "la tua segnalazione è stata presa in carico da un amministratore.\n"+
                "Buona giornata, lo staff di GoodVibes.");
    }
}
