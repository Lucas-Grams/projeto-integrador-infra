package br.com.pdsars.notificationapi.api.sender;

import br.com.pdsars.notificationapi.api.config.ApiProperties;
import br.com.pdsars.notificationapi.api.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
public class MailNotificationSender implements NotificationSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailNotificationSender.class);
    private final String fromText;
    private final List<EmailSender> senders;
    private final String redirectTo;
    private final boolean shouldRedirect;


    public MailNotificationSender(List<EmailSender> emailSenders,
                                  ApiProperties apiProperties) {
        this.senders = emailSenders;
        this.fromText = String.format("PDSA-RS <%s>", apiProperties.getMailDomain());
        this.redirectTo = apiProperties.getRedirectEmailsTo();
        this.shouldRedirect = !this.redirectTo.trim().equals("");
        if (shouldRedirect) {
            LOGGER.warn("Emails serão redirecionados para {}", redirectTo);
        }
    }

    @Override
    public void send(final NotificationMessage msg, final List<Account> us) {
        final EmailRequest.EmailRequestBuilder emailBuilder =
            EmailRequest.builder()
                .recipient(this.fromText)
                .subject(msg.getTitulo())
                .body(this.msgTemplate(msg.getCorpo()));
        try {
            us.forEach(u ->
                this.send(emailBuilder.destination(u.getEmail()).build()));
            LOGGER.info("Notificacao por email disparada para {}",
                us.stream()
                    .map(Account::getEmail)
                    .collect(Collectors.toList()));
        } catch (Exception ex) {
            LOGGER.error("erro ao enviar email ", ex);
        }
    }

    public CompletableFuture<Void> send(String subject, String body, String toEmail) {
        return CompletableFuture.runAsync(() -> {
            final EmailRequest e = EmailRequest.builder().body(this.msgTemplate(body))
                .destination(toEmail)
                .recipient(this.fromText)
                .subject(subject)
                .build();
            try {
                this.send(e);
                LOGGER.info("Email com assunto={} enviado para {}",
                    subject, toEmail);
            } catch (Exception ex) {
                LOGGER.error("Não foi possivel enviar email para "
                             + toEmail + " com assunto " + subject, ex);
            }

        });
    }

    private void send(final EmailRequest er) {
        if (shouldRedirect) {
            LOGGER.warn("Email redirecionado para {}, destinatario original: {}",
                redirectTo, er.getDestination());
            final EmailRequest redirected = new EmailRequest.EmailRequestBuilder()
                .destination(this.redirectTo)
                .body(er.getBody())
                .subject(er.getSubject())
                .recipient(er.getRecipient())
                .build();
            this.senders.forEach(s -> s.sendHtmlMail(redirected));
        } else {
            this.senders.forEach(s -> s.sendHtmlMail(er));
        }
    }


    private String msgTemplate(String body) {
        return
            """
                    <br>
                    <div style="border:1px solid #1f4b71;">
                        <div style="text-align:left;background-color:#54a579; padding:8px 15px 8px 15px">
                            <span style="color:white;font-size:16px;text-align:left">
                                <strong>Sistema de Notificação</strong>
                            </span>
                        </div>

                        <div style="text-align:left;padding:15px">
                            <div>
                """
            + body +
            """

                            </div>
                        </div>

                        <div style="text-align:left;background-color:#54a579;padding:1px 15px 1px 15px">
                            <p style="color:white;font-size:10px;text-align:left">
                                Não responda este e-mail.<br> Esta é uma notificação gerada automaticamente, portanto, não deve ser
                                respondida.
                                <br>
                            </p>
                        </div>
                    </div>
                    <br>
                """;
    }
}
