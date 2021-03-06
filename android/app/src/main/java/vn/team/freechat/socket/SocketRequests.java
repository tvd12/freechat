package vn.team.freechat.socket;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.entity.EzyZone;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import vn.team.freechat.constant.Commands;
import vn.team.freechat.request.GetContactsRequest;
import vn.team.freechat.request.SendSystemMessageRequest;
import vn.team.freechat.request.SendUserMessageRequest;

public final class SocketRequests {

    private SocketRequests() {}

    public static void sendGetContacts() {
        EzyApp app = getApp();
        if(app != null)
            app.send(new GetContactsRequest(0, 50));
    }

    public static void sendGetSuggestContacts() {
        EzyApp app = getApp();
        if(app != null)
            app.send(Commands.SUGGEST_CONTACTS);
    }

    public static void sendAddContact(String user) {
        sendAddContacts(Arrays.asList(user));
    }

    public static void sendSearchContacts(String keyword) {
        EzyApp app = getApp();
        if(app != null) {
            EzyObject data = EzyEntityFactory.newObjectBuilder()
                    .append("keyword", keyword)
                    .build();
            app.send(Commands.SEARCH_CONTACTS, data);
        }
    }

    public static void sendAddContacts(Collection<String> users) {
        EzyApp app = getApp();
        if(app != null) {
            EzyObject data = EzyEntityFactory.newObjectBuilder()
                    .append("target", users)
                    .build();
            app.send(Commands.ADD_CONTACTS, data);
        }
    }

    public static void sendSystemMessage(String message) {
        EzyApp app = getApp();
        if(app != null)
            app.send(new SendSystemMessageRequest(message));
    }

    public static void sendUserMessage(long channelId, String message) {
        EzyApp app = getApp();
        if(app != null)
            app.send(new SendUserMessageRequest(channelId, message));
    }

    private static EzyApp getApp() {
        EzyZone zone = getClient().getZone();
        if(zone == null)
            return null;
        return zone.getApp();
    }

    private static EzyClient getClient() {
        EzyClients clients = EzyClients.getInstance();
        return clients.getDefaultClient();
    }
}
