package br.com.pdsars.notificationapi.api.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * NotificationEvent
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent implements Serializable {
    private UUID uuid;
    private String title;
    private String body;
    private String userEmail;
}
