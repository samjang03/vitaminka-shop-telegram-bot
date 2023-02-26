package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.TelegramService;

import static ua.vitaminka.constants.Constants.CONTACTS;

@Component
public class ContactsHandler extends UserRequestHandler {

    private final TelegramService telegramService;

    public ContactsHandler(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate(), CONTACTS);
    }

    @Override
    public void handle(UserRequest request) {
        telegramService.sendMessage(request.getChatId(),
                "Наші контакти:\n" +
                "\uD83D\uDCDE +38 (067) 375-99-09\n" +
                "\uD83D\uDCDE +38 (066) 375-99-09\n" +
                "\uD83D\uDCDE +38 (073) 375-99-09\n" +
                "\uD83D\uDCE7 info@vitaminka.com.ua");
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
