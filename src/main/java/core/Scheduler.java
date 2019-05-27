package core;

import model.Message;

import java.util.List;
import java.util.TimerTask;

public class Scheduler extends TimerTask {

    private final HtmlPageParser htmlPageParser = new HtmlPageParser();
    private final MessageSender messageSender = new MessageSender();

    @Override
    public void run() {
        System.out.println("Refreshing list");
        List<Message> messages = htmlPageParser.parseHtmlPage();
        messageSender.sendMessage(messages);
    }
}
