package br.com.pdsars.notificationapi.clientlibrary;

import com.github.scribejava.apis.KeycloakApi;

public class NonContextedKeycloakApi extends KeycloakApi {

    public NonContextedKeycloakApi(String baseUrlWithRealm) {
        super(baseUrlWithRealm);
    }
}
