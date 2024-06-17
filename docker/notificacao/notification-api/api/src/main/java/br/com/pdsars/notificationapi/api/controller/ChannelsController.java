package br.com.pdsars.notificationapi.api.controller;

import br.com.pdsars.notificationapi.api.Constants;
import br.com.pdsars.notificationapi.api.dto.ChannelDTO;
import br.com.pdsars.notificationapi.api.dto.NotificationDTO;
import br.com.pdsars.notificationapi.api.service.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/channels")
@Validated
public class ChannelsController {
    private final ChannelService channelservice;

    public ChannelsController(ChannelService channelservice) {
        this.channelservice = channelservice;
    }

    @GetMapping
    List<ChannelDTO> listChannels() {
        // FIXME: grab clientId from auth context
        return this.channelservice.listChannels(Constants.MOCKED_CLIENT_ID);
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PostMapping("/{channelId}")
    Map<String, UUID> sendNotification(
        @PathVariable String channelId,
        @RequestBody NotificationDTO notification
    ) {
        return this.channelservice.sendNotification(channelId, notification);
    }
}
