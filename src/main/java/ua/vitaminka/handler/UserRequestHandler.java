package ua.vitaminka.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import ua.vitaminka.model.user.UserRequest;

public abstract class UserRequestHandler {

    public abstract boolean isApplicable(UserRequest request);
    public abstract void handle(UserRequest dispatchRequest);
    public abstract boolean isGlobal();

    public boolean isCommand(Update update, String command) {
        return update.hasMessage() && update.getMessage().isCommand()
                && update.getMessage().getText().equals(command);
    }

    public boolean isTextMessage(Update update, String text) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(text);
    }
}
