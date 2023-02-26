package ua.vitaminka.model.user;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private Update update;
    private Long chatId;
    private UserSession userSession;
}
