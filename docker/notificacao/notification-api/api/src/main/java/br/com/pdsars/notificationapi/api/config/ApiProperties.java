package br.com.pdsars.notificationapi.api.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;


@Data
@ConfigurationProperties("api")
@Validated
public class ApiProperties {
    @NotBlank
    @Email
    private String mailDomain;

    @Valid
    private Aws aws;
    private boolean sendEmail = true;
    @Email
    private String redirectEmailsTo = "";

    @Data
    public static class Aws implements AwsCredentials {
        @NotBlank
        @Pattern(regexp = "\\w+")
        @Length(min = 16, max = 128)
        private String accessKeyId;
        @NotBlank
        private String secretAccessKey;

        @Override
        public String accessKeyId() {
            return this.accessKeyId;
        }

        @Override
        public String secretAccessKey() {
            return this.secretAccessKey;
        }

         public AwsCredentialsProvider toStaticCredentialsProvider() {
             return StaticCredentialsProvider.create(this);
         }
    }
}
