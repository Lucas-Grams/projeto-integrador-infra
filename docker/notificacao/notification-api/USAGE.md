# Send notification
To send a notification is as easy as sending a http `POST` to
`/v1/notifications`.
The only contrain imposed, is that the request needs to be authenticated with
an OAuth2 service account that has a role `create_notifications`.

```java
public class NotificationRequest {
    NotificationMessageRequst  message;
    List<String>               userEmails;

    public static class NotificationMessageRequst {
        String          title;
        String          body;
    }
}
```


# New API with categories and channels
## Create a categories
```typescript    
interface Group {
    id: string // ID of the category
    name: string // User-visible name
}
```

```http
POST /v1/categories
Content-Type: application/json

[
  {
    "id": "suino";
    "name": "Suino";
  },
  {
    "id": "aves";
    "name": "Aves";
  },
]
```

```json
POST /v1/categories
```

## Create a channel
```typescript
interface Channel {
  id: string // id of the channel
  name: string // User-visible name of the channel
  importance: 'low' | 'default' | 'high' = 'default'  
  description?: string // Extended description for the account
  groupName: string = 'others'
}
```

`POST /v1/channels`
```json
[
  {
    "id": "laudo_neg_suino",
    "name": "Laudo Negativo Emitido",
    "description": "Notificar quanto laboratorio emitir um laudo com todos os
    resultados negativos",
    "groupId": "suino"
  },
  {
    "id": "laudo_pos_aves",
    "name": "Laudo Positivo Emitido",
    "description": "Notificar quanto laboratorio emitir um laudo que contem um
    resultado positivo",
    "importance": "high",
    "groupId": "aves"
  }
]
```


## Send a notification 
**POST /v1/channels/{channel_id}**
Send a message to all `users` on the channel with id `channel_id`.

```typescript
interface Notification {
  title: string
  body: string
  users: string[] // list of emails to sent the notification
  meta: { [key: string]: string } // metada to attatch to the message, so it can be used to have actions in the ui
}
```


**Exemple**:
POST /v1/channels/laudo_pos_aves
```json
{
  "title": "Laudo positivo emitido",
  "body": "Novo Laudo positivo emito por laboratorio x...",
  "users": [ "d.ebling8@gmail.com", "glenio.descovi@gmail.com" ],
  "meta": {
      "laudo_id": "12",
      "estabelecimento_id": "42",
   }
}
```
