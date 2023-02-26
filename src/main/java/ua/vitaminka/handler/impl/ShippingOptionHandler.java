package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerShippingQuery;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingOption;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.TelegramService;

import java.util.List;

@Component
public class ShippingOptionHandler extends UserRequestHandler {

    private final TelegramService telegramService;

    public ShippingOptionHandler(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return request.getUpdate().hasShippingQuery();
    }

    @Override
    public void handle(UserRequest request) {
        ShippingOption option1 = createShippingOption(1, "Нова Пошта (відділення)", new LabeledPrice("Доставка: ", 70 * 100));
        ShippingOption option2 = createShippingOption(2, "Нова Пошта (кур'єрська доставка)", new LabeledPrice("Доставка: ", 90 * 100));

        AnswerShippingQuery answerShippingQuery = AnswerShippingQuery.builder()
                .shippingQueryId(String.valueOf(request.getUpdate().getShippingQuery().getId()))
                .ok(true)
                .shippingOptions(List.of(option1, option2))
                .build();

        telegramService.executeInvoice(answerShippingQuery);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    private ShippingOption createShippingOption(int id, String title, LabeledPrice labeledPrice) {
        return ShippingOption.builder()
                .id(String.valueOf(id))
                .title(title)
                .price(labeledPrice)
                .build();
    }
}
