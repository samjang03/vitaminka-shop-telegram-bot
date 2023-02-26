package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.helper.KeyboardHelper;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.TelegramService;

@Component
public class StartCommandHandler extends UserRequestHandler {

    private static String command = "/start";

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;

    public StartCommandHandler(TelegramService telegramService, KeyboardHelper keyboardHelper) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildStartMenu();
        telegramService.sendMessage(request.getChatId(),
                "Привіт! Я віртуальна помічниця інтернет-магазину Vitaminka\uD83C\uDF3F " +
                        "Готові відкрити для себе силу наших добавок преміум-класу?", replyKeyboard);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
