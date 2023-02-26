package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.TelegramService;

@Component
public class StopBotCommandHandler extends UserRequestHandler {

    private static String command = "/stop";

    private final TelegramService telegramService;

    public StopBotCommandHandler(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        telegramService.sendMessage(request.getChatId(),
                "Дякуємо, що обрали наш магазин\uD83D\uDE0A Бережіть себе та будьте здорові!");
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
