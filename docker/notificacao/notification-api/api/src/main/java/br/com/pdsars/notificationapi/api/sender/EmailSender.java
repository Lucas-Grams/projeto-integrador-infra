package br.com.pdsars.notificationapi.api.sender;

public interface EmailSender {
    void sendHtmlMail(final EmailRequest e);
}
