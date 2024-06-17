package br.com.pdsars.notificationapi.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ChannelsControllerTest {

    private static final String GROUP_REQUEST = """
    [
       {
         "id": "suino",
         "name": "Suino"
       },
       {
         "id": "aves",
         "name": "Aves"
       }
     ]
    """;

	private static final String CHANNEL_REQUEST = """
    [
      {
        "id": "laudo_neg_suino",
        "name": "Laudo Negativo Emitido",
        "description": "Notificar quanto laboratorio emitir um laudo com todos os resultados negativos",
        "importance": "high",
        "groupId": "suino"
      },
      {
        "id": "laudo_pos_aves",
        "name": "Laudo Positivo Emitido",
        "description": "Notificar quanto laboratorio emitir um laudo que contem um resultado positivo",
        "importance": "high",
        "groupId": "aves"
      }
    ]
    """;

	@Autowired
    private MockMvc mockMvc;
//
//    @Test
//    @Order(1)
//    void testPutGroups() throws Exception {
//        this.mockMvc.perform(
//                post("/v1/groups")
//                        .content(GROUP_REQUEST)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(2)
//    void putChannels() throws Exception {
//        this.mockMvc.perform(
//                post("/v1/channels")
//                        .content(CHANNEL_REQUEST)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    @Order(3)
    void sendNotification() throws Exception {
        final var channel = "laudo_pos_aves";
        final var request = """
        {
          "title": "Laudo positivo emitido",
          "body": "Novo Laudo positivo emito por laboratorio x...",
          "users": [ "d.ebling8@gmail.com" ],
          "meta": {
              "laudo_id": "12",
              "estabelecimento_id": "42"
           }
        }
        """;
        this.mockMvc.perform(
                post("/v1/channels/" + channel)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }
}
