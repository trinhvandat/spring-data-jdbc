package aibles.springdatajdbc.userservice.mail.dto.req;

public class MailRequestDTO {

    private String receiver;
    private String subject;
    private String message;

    public MailRequestDTO() {
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
