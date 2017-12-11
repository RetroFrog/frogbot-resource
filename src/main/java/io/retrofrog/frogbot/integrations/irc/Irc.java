package io.retrofrog.frogbot.integrations.irc;

import io.retrofrog.frogbot.integrations.irc.utils.IrcString;
import io.retrofrog.frogbot.integrations.irc.utils.IrcStyle;
import net.engio.mbassy.listener.Handler;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.client.ClientConnectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Irc {
    private static final Logger log = LoggerFactory.getLogger(Irc.class);

    private Client client;
    private boolean enabled;
    private String nick;
    private String channel;

    public Irc(@Value("${irc.enabled:false}") boolean enabled, @Value("${irc.host:}") String host,
               @Value("${irc.port:6667}") int port, @Value("${irc.ssl:false}") boolean ssl,
               @Value("${irc.nick:frogbot}") String nick, @Value("${irc.password:}") String password,
               @Value("${irc.channel:}") String channel) {

        this.enabled = enabled;
        this.nick = nick;
        this.channel = channel;

        if (!enabled)
            return;

        client = Client.builder().serverHost(host).serverPort(port).secure(ssl).user("Frogbot").nick(nick).build();
        client.getEventManager().registerEventListener(new Listener());
        client.addChannel(channel);

        log.info("IRC connecting...");
    }

    public void send(String message) {
        if (!enabled)
            return;
        client.sendMessage(channel, message);
    }

    public static class Listener {
        @Handler
        public void onClientConnected(ClientConnectedEvent event) {
            log.info("IRC connected!");
        }

        @Handler
        public void onUserJoinChannel(ChannelJoinEvent event) {
            if (event.getClient().isUser(event.getUser())) { // It's me!
                event.getChannel().sendMessage(String.format("%s %s hopping to action! %s",
                        new IrcString(" ribbit ", IrcStyle.BackgroundGreen, IrcStyle.Grey, IrcStyle.Bold),
                        new IrcString(event.getUser().getNick(), IrcStyle.Teal, IrcStyle.Bold),
                        new IrcString(" ribbit ", IrcStyle.BackgroundGreen, IrcStyle.Grey, IrcStyle.Bold)));
                return;
            }

            event.getChannel().sendMessage("Welcome, " + event.getUser().getNick() + "! :3");
        }
    }
}
