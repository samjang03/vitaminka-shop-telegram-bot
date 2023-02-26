package ua.vitaminka.handler.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.service.SupplementService;
import ua.vitaminka.service.TelegramService;

import java.math.BigDecimal;

import static ua.vitaminka.constants.Constants.GOODS;

@Component
public class ShowGoodsHandler extends UserRequestHandler {

    private final TelegramService telegramService;

    private final SupplementService supplementService;

    @Value("${bot.providerToken}")
    private String providerToken;

    public ShowGoodsHandler(TelegramService telegramService, SupplementService supplementService) {
        this.telegramService = telegramService;
        this.supplementService = supplementService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate(), GOODS);
    }

    @Override
    public void handle(UserRequest request) {
        supplementService.getAllSupplements().forEach(s -> {
            SendInvoice invoice = SendInvoice.builder()
                    .chatId(request.getChatId())
                    .title(s.getName())
                    .description(s.getManufacturer() + "\n" + s.getCountryOfOrigin())
                    .providerToken(providerToken)
                    .currency("UAH")
                    .price(new LabeledPrice("Ціна: ", s.getPrice().multiply(BigDecimal.valueOf(100L)).intValue()))
                    .needShippingAddress(true)
                    .needName(true)
                    .needPhoneNumber(true)
                    .isFlexible(true)
                    .photoUrl(s.getPhotoUrl())
                    .payload("invoice-payload-" + s.getId())
                    .startParameter("")
                    .build();
            telegramService.executeInvoice(invoice);
        });
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
