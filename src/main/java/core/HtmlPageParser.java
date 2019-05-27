package core;

import lombok.SneakyThrows;
import model.Message;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static core.Utils.isEmpty;

class HtmlPageParser {

    @SneakyThrows
    List<Message> parseHtmlPage() {
        List<Message> messages = new ArrayList<>();

        Document document = createConnection();

        Elements sections = document.getElementsByTag("section");

        sections.forEach(section -> {
            Message message = new Message();
            Elements links = section.getElementsByClass("job-title-link break visited");

            links.forEach(link -> {
                String url = "https://www.upwork.com" + link.attr("href");
                message.setUrl(url);
                message.setDescription(link.text());
                System.out.println(url);
                System.out.println(link.text());
            });

            Elements prices = section.select(".js-budget");

            prices.forEach(price -> {
                message.setPrice(price.text());
                System.out.println(price.text());
            });

            Elements postDates = section.getElementsByTag("time");

            postDates.forEach(date -> {
                System.out.println(date.attr("datetime"));
                message.setPostDate(OffsetDateTime.parse(date.attr("datetime")));
            });

            if (!isEmpty(message)) {
                messages.add(message);
            }
        });

        return messages;
    }

    @SneakyThrows
    private Document createConnection() {
        String url = "https://www.upwork.com/o/jobs/browse/?q=";
        String filter = "aso";
        Connection conn = Jsoup
                .connect(url + URLEncoder.encode(filter, "UTF-8"))
                .userAgent("Chrome/33.0.1750.152")
                .referrer("http://www.google.com");

        Thread.sleep(4000);

        return conn.get();
    }
}
