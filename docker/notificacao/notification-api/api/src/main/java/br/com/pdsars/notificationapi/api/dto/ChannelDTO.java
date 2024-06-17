package br.com.pdsars.notificationapi.api.dto;

import java.util.HashMap;
import java.util.Map;
import br.com.pdsars.notificationapi.api.model.Channel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChannelDTO {
    @NotBlank(message = "`id` of the channel can not be blank")
    private String id;
    @NotBlank(message = "`name` of the channel can not be blank")
    private String name;
    @NotBlank(message = "`description` of the channel can not be blank")
    private String description;
    @NotNull(message = "`importance` of the channel can not be null")
    private Channel.Importance importance = Channel.Importance.DEFAULT;
    // can be null
    private String categoryId;

    @NotNull(message = "`meta` of the channel can not be null")
    private Map<String, String> meta = new HashMap<>();

    public ChannelDTO() { }

    public ChannelDTO(final Channel channel) {
        this.id = channel.getExternalId();
        this.name = channel.getName();
        this.description = channel.getDescription();
        this.importance = channel.getImportance();
        this.meta = new HashMap<>(channel.getMeta());
    }

    public Channel toChannel() {
        final var channel = new Channel();
        channel.setExternalId(this.id);
        channel.setName(this.name);
        channel.setDescription(this.description);
        channel.setImportance(this.importance);
        channel.setMeta(this.meta);
        return channel;
    }
}
