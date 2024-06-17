package br.com.pdsars.notificationapi.api.sender;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailRequest {
    String recipient;
    String subject;
    String body;
    String destination;
}
