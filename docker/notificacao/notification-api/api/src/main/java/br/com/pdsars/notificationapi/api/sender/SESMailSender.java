package br.com.pdsars.notificationapi.api.sender;

import br.com.pdsars.notificationapi.api.model.NotificationEvent;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;


public class SESMailSender implements EmailSender {
    private final        SesClient sesClient;
    private static final String    CHARSET = "UTF-8";

    public SESMailSender(AwsCredentialsProvider provider, Region r) {

        this.sesClient = SesClient.builder()
            .credentialsProvider(provider)
            .region(r)
            .build();
    }

    @Override
    public void sendHtmlMail(EmailRequest email) {
        final Content.Builder contentBuilder = Content.builder().charset(CHARSET);

        final var m = Message.builder()
            .body(
                Body.builder()
                .html(contentBuilder.data(email.getBody()).build())
                .build()
            )
            .subject(contentBuilder.data(email.getSubject()).build())
            .build();

        final SendEmailRequest req = SendEmailRequest.builder()
            .message(m)
//            .tags(MessageTag.builder().name("uuid").value(notificationEvent.getUuid().toString()).build())
            .destination(
                Destination.builder()
                .toAddresses(email.getDestination())
                .build()
            )
            .configurationSetName("SESEvents")
            .source("nao-responda@pdsa-rs.com.br")
            .build();

        final SendEmailResponse resp = this.sesClient.sendEmail(req);
    }
}
