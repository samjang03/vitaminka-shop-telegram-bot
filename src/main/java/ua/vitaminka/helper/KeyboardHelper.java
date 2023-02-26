package ua.vitaminka.helper;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


import java.util.List;

import static ua.vitaminka.constants.Constants.*;

@Component
public class KeyboardHelper {

    public InlineKeyboardMarkup buildStartMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button = createButton("Так \uD83D\uDE09", GET_READY);

        List<InlineKeyboardButton> row = List.of(button);
        List<List<InlineKeyboardButton>> rows = List.of(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup buildMainMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton goodsButton = createButton("Товари \uD83D\uDC8A", GOODS);
        InlineKeyboardButton contactsButton = createButton("Контакти ☎", CONTACTS);
        InlineKeyboardButton sheduleButton = createButton("Графік роботи \uD83D\uDCC5", SHEDULE);
        InlineKeyboardButton reviewsButton = InlineKeyboardButton.builder()
                                                            .text("Відгуки \uD83D\uDC81")
                                                            .callbackData(REVIEWS)
                                                            .url("https://vitaminka.com.ua/otzyvy")
                                                            .build();

        List<InlineKeyboardButton> row1 = List.of(goodsButton, contactsButton);
        List<InlineKeyboardButton> row2 = List.of(sheduleButton, reviewsButton);

        List<List<InlineKeyboardButton>> rows = List.of(row1, row2);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton createButton(String caption, String data) {
        return InlineKeyboardButton.builder()
                .text(caption)
                .callbackData(data)
                .build();
    }
}
