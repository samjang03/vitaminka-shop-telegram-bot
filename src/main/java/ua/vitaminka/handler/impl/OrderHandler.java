package ua.vitaminka.handler.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.payments.OrderInfo;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import ua.vitaminka.handler.UserRequestHandler;
import ua.vitaminka.model.Order;
import ua.vitaminka.model.Supplement;
import ua.vitaminka.model.user.UserRequest;
import ua.vitaminka.model.user.UserSession;
import ua.vitaminka.service.OrderService;
import ua.vitaminka.service.SupplementService;
import ua.vitaminka.service.TelegramService;

import java.util.Arrays;
import java.util.List;

@Component
public class OrderHandler extends UserRequestHandler {

    private final TelegramService telegramService;

    private final OrderService orderService;

    private final SupplementService supplementService;

    public OrderHandler(TelegramService telegramService, OrderService orderService, List<Supplement> supplements, SupplementService supplementService) {
        this.telegramService = telegramService;
        this.orderService = orderService;
        this.supplementService = supplementService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        if (request.getUpdate().getMessage() != null)
            return request.getUpdate().getMessage().hasSuccessfulPayment();
        return false;
    }


    @Override
    public void handle(UserRequest request) {
        Order order = makeOrder(request);
        Supplement supplement = supplementService.getAllSupplements().stream()
                        .filter(s -> s.getId() == order.getSup_id())
                                .findFirst().get();
        telegramService.sendMessage(request.getChatId(),
                "Чудовий вибір! Ваше замовлення обробляється та незабаром буде відправлено✅\n");
        telegramService.sendMessage(request.getChatId(),
                "Інформація про замовлення:\n" +
                        "\uD83D\uDCE6 Замовлений товар: " + supplement.getName() + "\n" +
                        "\uD83D\uDC64 Отримувач: " + order.getName() + "\n" +
                        "\uD83D\uDCDE Номер телефону: +" + order.getPhoneNumber() + "\n" +
                        "\uD83D\uDEFA Адреса доставки: " + order.getCity() + ", " +
                        order.getAddress() + "\n" +
                        "Всього до сплати: " + order.getPrice() + " UAH");
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    private Order makeOrder(UserRequest request) {
        SuccessfulPayment successfulPayment = request.getUpdate()
                .getMessage()
                .getSuccessfulPayment();
        OrderInfo orderInfo = successfulPayment.getOrderInfo();
        Order order = new Order();
        order.setName(orderInfo.getName());
        String sup_id = Arrays.stream(successfulPayment.getInvoicePayload()
                .split("-"))
                .filter(str -> str.matches("[0-9]+"))
                .findFirst()
                .get();
        order.setSup_id(Integer.valueOf(sup_id));
        order.setPhoneNumber(orderInfo.getPhoneNumber());
        order.setCity(orderInfo.getShippingAddress().getCity());
        order.setAddress(orderInfo.getShippingAddress().getStreetLine1());
        double price = successfulPayment.getTotalAmount()/100.00;
        order.setPrice(price);

        UserSession session = request.getUserSession();
        session.addOrder(order);

        return orderService.saveOrder(order);
    }
}
