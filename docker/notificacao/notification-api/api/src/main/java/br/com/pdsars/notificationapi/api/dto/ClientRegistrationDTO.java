package br.com.pdsars.notificationapi.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientRegistrationDTO {
    private List<CategoryDTO> categories = new ArrayList<>();

    @NotEmpty(message = "List of `channels` can not be empty")
    private List<ChannelDTO> channels = new ArrayList<>();
}
