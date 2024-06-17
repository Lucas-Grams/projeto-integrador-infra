package br.com.pdsars.notificationapi.api.dto;

import br.com.pdsars.notificationapi.api.model.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class NotificationDTO {
    @NotBlank(message = "`title` of the notification can not be blank")
    private String title;

    @NotBlank(message = "`body` of the notification can not be blank")
    private String body;

    @NotEmpty(message = "`users` of the notification can not be empty")
    private List<String> users;

    @NotNull(message = "`meta` can not be null")
    private Map<String, String> meta = new HashMap<>();


    public Notification toNotification() {
        final var n = new Notification();
        n.setTitle(this.title);
        n.setBody(this.body);
        n.setMeta(this.meta);
        return n;
    }

}
