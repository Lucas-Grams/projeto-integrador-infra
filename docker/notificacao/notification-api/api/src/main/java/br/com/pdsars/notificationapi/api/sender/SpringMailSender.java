package br.com.pdsars.notificationapi.api.sender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


public class SpringMailSender implements EmailSender {
    private static final String CHARSET = "UTF-8";
    private final JavaMailSender javaMailSender;

    public SpringMailSender(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendHtmlMail(EmailRequest email) {
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, CHARSET);

        try {
            helper.setFrom(email.getRecipient());
            helper.setTo(email.getDestination());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);
        } catch (MessagingException ex) {
            throw new RuntimeException("Erro ao criar mimeMessage", ex);
        }
        this.javaMailSender.send(helper.getMimeMessage());
    }
}
