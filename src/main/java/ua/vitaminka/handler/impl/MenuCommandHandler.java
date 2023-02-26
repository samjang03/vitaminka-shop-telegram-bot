package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.helper.KeyboardHelper;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.TelegramService;

import static ua.vitaminka.constants.Constants.GET_READY;

@Component
public class MenuCommandHandler extends UserRequestHandler {

    private static String command = "/menu";

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;

    public MenuCommandHandler(TelegramService telegramService, KeyboardHelper keyboardHelper) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate(), GET_READY) || isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMainMenu();
        telegramService.sendMessage(request.getChatId(), "Обирайте з меню нижче:⤵", replyKeyboard);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
