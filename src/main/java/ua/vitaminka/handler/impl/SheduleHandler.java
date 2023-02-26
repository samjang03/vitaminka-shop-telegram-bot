package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.TelegramService;

import static ua.vitaminka.constants.Constants.SHEDULE;

@Component
public class SheduleHandler extends UserRequestHandler {

    private final TelegramService telegramService;

    public SheduleHandler(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate(), SHEDULE);
    }

    @Override
    public void handle(UserRequest request) {
        telegramService.sendMessage(request.getChatId(),
                "Графік роботи:\n" +
                        "\uD83D\uDD57 Пн - Пт: 9:00 - 19:00\n" +
                        "\uD83D\uDD57 Субота: 10:00 - 14:00\n" +
                        "\uD83C\uDFD6 Неділя - вихідний");
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
