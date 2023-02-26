package ua.vitaminka.model.user;

import lombok.*;
import ua.vitaminka.model.Order;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSession {
    private Long chatId;
    private List<Order> orderList;

    public Long getChatId() {
        return chatId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public boolean addOrder(Order order) {
        return orderList.add(order);
    }
}
