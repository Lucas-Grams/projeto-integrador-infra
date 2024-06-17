package br.com.pdsars.notificationapi.api.sender;

import br.com.pdsars.notificationapi.api.config.ApiProperties;
import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import software.amazon.awssdk.regions.Region;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class MailSenderConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MailSenderConfiguration.class);
    @Value("#{'${spring.profiles.active}' == 'prod'}")
    private boolean isProd;
    private final ApiProperties apiProperties;

    public MailSenderConfiguration(final ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    @Bean
    List<EmailSender> emailSender(Optional<JavaMailSender> javaMailSender) {
        if (!this.apiProperties.isSendEmail()) {
            log.warn("Envio de emails desativado!");
            return List.of(new EmailSenderMock());
        }

        final var senders = new ArrayList<EmailSender>();

        if (this.isProd) {
            log.info("Registrando bean para envio de emails via AWS SES");
            senders.add(
                new SESMailSender(
                    this.apiProperties.getAws().toStaticCredentialsProvider(),
                    Region.US_EAST_1
                )
            );
        }

        log.info("Registrando bean para envio de emails via GMail");
        senders.add(
            new SpringMailSender(
                javaMailSender.orElseThrow(
                    () -> new NoSuchBeanDefinitionException("No javaMailSender found")
                )
            )
        );
        return senders;
    }

    private static class EmailSenderMock implements EmailSender {
        private static final Logger log = LoggerFactory.getLogger(EmailSenderMock.class);

        @Override
        public void sendHtmlMail(EmailRequest e) {
            try {
                Thread.sleep(Duration.ofSeconds(1).toMillis());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            log.info(
                "(MOCK) Envio de email para {}, assunto: {}",
                e.getDestination(), e.getSubject()
            );
        }
    }
}
