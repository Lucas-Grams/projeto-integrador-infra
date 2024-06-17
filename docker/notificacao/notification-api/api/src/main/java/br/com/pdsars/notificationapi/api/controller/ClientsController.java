package br.com.pdsars.notificationapi.api.controller;

import br.com.pdsars.notificationapi.api.dto.ClientRegistrationDTO;
import br.com.pdsars.notificationapi.api.dto.ClientRegistrationResponse;
import br.com.pdsars.notificationapi.api.model.Client;
import br.com.pdsars.notificationapi.api.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/v1/clients")
public class ClientsController {
    private final ClientService clientService;

    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }


    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/me")
    ClientRegistrationResponse registerClient(@RequestBody ClientRegistrationDTO clientRegistration) {
        return ClientRegistrationResponse.fromClient(
            this.clientService.registerClient(clientRegistration)
        );
    }
}
