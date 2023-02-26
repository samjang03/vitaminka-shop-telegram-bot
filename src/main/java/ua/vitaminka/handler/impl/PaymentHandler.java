package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.TelegramService;

@Component
public class PaymentHandler extends UserRequestHandler {

    private final TelegramService telegramService;

    public PaymentHandler(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return request.getUpdate().hasPreCheckoutQuery();
    }

    @Override
    public void handle(UserRequest request) {
        PreCheckoutQuery preCheckoutQuery = request.getUpdate().getPreCheckoutQuery();
        AnswerPreCheckoutQuery answer = AnswerPreCheckoutQuery.builder()
                .preCheckoutQueryId(preCheckoutQuery.getId())
                .ok(true)
                .build();
        telegramService.executeInvoice(answer);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
