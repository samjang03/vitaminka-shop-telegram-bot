package ua.vitaminka;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.model.user.UserSession;
import ua.vitaminka.repository.SupplementRepository;
import ua.vitaminka.service.UserSessionService;

@Slf4j
@Component
public class VitaminkaBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.username}")
    private String botUsername;

    private final Dispatcher dispatcher;
    private final UserSessionService userSessionService;

    public VitaminkaBot(Dispatcher dispatcher, UserSessionService userSessionService, SupplementRepository repo) {
        this.dispatcher = dispatcher;
        this.userSessionService = userSessionService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        UserRequest userRequest = null;
        if(update.hasMessage() && update.getMessage().hasText()) {
            userRequest = isMessage(update);
        } else if(update.hasMessage() && update.getMessage().hasSuccessfulPayment()) {
            userRequest = isMessage(update);
        } else if (update.hasCallbackQuery()) {
            userRequest = isCallbackQuery(update);
        } else if (update.hasPreCheckoutQuery()) {
            userRequest = isPreCheckoutQuery(update);
        } else if(update.hasShippingQuery()) {
            userRequest = isShippingQuery(update);
        } else {
            log.warn("Unexpected update from user");
        }

        boolean dispatched = dispatcher.dispatch(userRequest);

        if (!dispatched) {
            log.warn("Unexpected update from user");
        }
    }

    private UserRequest isMessage(Update update) {
        String textFromUser = update.getMessage().getText();

        Long userId = update.getMessage().getFrom().getId();
        String userFirstName = update.getMessage().getFrom().getFirstName();

        log.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

        Long chatId = update.getMessage().getChatId();

        UserSession session = userSessionService.getSession(chatId);

        return UserRequest
                .builder()
                .update(update)
                .userSession(session)
                .chatId(chatId)
                .build();
    }

    private UserRequest isCallbackQuery(Update update) {
        String textFromUser = update.getCallbackQuery().getMessage().getText();

        Long userId = update.getCallbackQuery().getMessage().getFrom().getId();
        String userFirstName = update.getCallbackQuery().getMessage().getFrom().getFirstName();

        log.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        UserSession session = userSessionService.getSession(chatId);

        return UserRequest
                .builder()
                .update(update)
                .userSession(session)
                .chatId(chatId)
                .build();
    }

    private UserRequest isPreCheckoutQuery(Update update) {
        String invoicePayload = update.getPreCheckoutQuery().getInvoicePayload();
        String userFirstName = update.getPreCheckoutQuery().getFrom().getFirstName();
        Long userId = update.getPreCheckoutQuery().getFrom().getId();

        log.info("[{}, {}] : {}", userId, userFirstName, invoicePayload);

        return UserRequest
                .builder()
                .update(update)
                .build();
    }

    private UserRequest isShippingQuery(Update update) {
        String invoicePayload = update.getShippingQuery().getInvoicePayload();
        String userFirstName = update.getShippingQuery().getFrom().getFirstName();
        Long userId = update.getShippingQuery().getFrom().getId();

        log.info("[{}, {}] : {}", userId, userFirstName, invoicePayload);

        return UserRequest
                .builder()
                .update(update)
                .build();
    }
}
