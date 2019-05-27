package core;

import lombok.SneakyThrows;
import model.Message;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;

class MessageSender {

    private static OffsetDateTime LAST_POSTED_TIME = OffsetDateTime.of(2019, 5, 25, 10, 10, 0, 0, UTC);
//    private static OffsetDateTime LAST_POSTED_TIME = OffsetDateTime.now(UTC);

    @SneakyThrows
    void sendMessage(List<Message> constructedMessages) {
        final String urlString = "https://api.telegram.org/bot850495706:AAEvFZaj22G3OXza6hSidEF6tbb1iUAuprE/sendMessage?chat_id=@etoProstaShedevar&text=";

        List<Message> messages = constructedMessages
                .stream()
                .filter(message -> message.getPostDate().isAfter(LAST_POSTED_TIME))
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder();

        for (Message message : messages) {
            stringBuilder
                    .append("Description: \n")
                    .append(message.getDescription());

            if (nonNull(message.getPrice())) {
                stringBuilder.append("\n Price: \n")
                        .append(message.getPrice());
            }

            stringBuilder
                    .append("\n Link: \n")
                    .append(message.getUrl())
                    .append("\n\n\n");

            String text = URLEncoder.encode(stringBuilder.toString(), "UTF-8");

            String finalUrl = urlString + text;

            URL url = new URL(finalUrl);
            URLConnection conn = url.openConnection();

            new BufferedInputStream(conn.getInputStream());

            Thread.sleep(2000);

        }
        resetLastPostedTime(messages);
    }

    private static void resetLastPostedTime(List<Message> messages) {
        LAST_POSTED_TIME = messages
                .stream()
                .max(Comparator.comparing(Message::getPostDate))
                .map(Message::getPostDate)
                .orElse(LAST_POSTED_TIME);

        System.out.println("Last posted time changed: " + LAST_POSTED_TIME);
    }
}
