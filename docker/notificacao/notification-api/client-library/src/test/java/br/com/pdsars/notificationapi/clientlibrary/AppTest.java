package br.com.pdsars.notificationapi.clientlibrary;

import br.com.pdsars.notificationapi.clientlibrary.model.Category;
import br.com.pdsars.notificationapi.clientlibrary.model.Channel;
import br.com.pdsars.notificationapi.clientlibrary.model.Channel.Importance;
import br.com.pdsars.notificationapi.clientlibrary.model.Notification;
import org.junit.jupiter.api.Test;

import java.util.*;

public class AppTest {
    @Test
     void testNotification() {
         final OAuth2NotificationClient.Builder builder =
                 new OAuth2NotificationClient.Builder(
                     "http://localhost:8080",
                     "client-app",
                     "MrDNjm1XYTtWGGOSFKGpUufYh2SNs0Jc",
                     "http://localhost:9090/realms/master"
                 );

        final Category avesCategory = new Category("aves", "Aves");
        final List<Category> categories = new ArrayList<>();
        categories.add(avesCategory);

        final List<Channel> channels = new ArrayList<>();
        final String channelId = "laudo_pos_suino";
        final Map<String, String> channelMeta = new HashMap<>();
        channelMeta.put("roles", "rt, admin");
        channels.add(
            new Channel.Builder()
            .id(channelId)
            .name("Laudo Positivo Emitido")
            .description("Notificar quanto laboratorio emitir um laudo que contem um resultado positivo")
            .importance(Importance.HIGH)
            .category(avesCategory)
            .meta(channelMeta)
            .build()
        );


        final NotificationClient client = builder.withCategories(categories)
                                                 .withChannels(channels)
                                                 .build();
        final List<String> users = new ArrayList<>();
        users.add("d.ebling8@gmail.com");
        final HashMap<String, String> meta = new HashMap<>();
        meta.put("laudo_id", "12");
        meta.put("estabelecimento_id", "42");

        final Notification notification = new Notification.Builder()
        .title("Notificacao teste")
        .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ligula lectus, rhoncus in nibh iaculis, hendrerit pharetra sapien. Nulla gravida felis et lacus viverra vulputate. Nam libero arcu, placerat sit amet malesuada ut, maximus sit amet neque. Nunc non arcu ipsum. Sed sed arcu congue, pretium est ac, cursus augue. In in mi rhoncus, vestibulum eros in, blandit mi. Duis accumsan venenatis tempor. Nullam et imperdiet nibh, eu rutrum turpis. Proin vel placerat augue. Fusce viverra lorem sit amet mollis malesuada. Vestibulum malesuada dapibus nibh, id congue tortor rutrum nec. Sed et lectus sed est luctus dapibus. ")
        .users(users)
        .meta(meta)
        .build();
        client.send(channelId, notification);
     }
}
