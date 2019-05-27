package core;

import lombok.NoArgsConstructor;
import model.Message;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class Utils {

    static boolean isEmpty(Message message) {
        return message.getUrl() == null && message.getPrice() == null && message.getDescription() == null && message.getPostDate() == null;
    }
}
