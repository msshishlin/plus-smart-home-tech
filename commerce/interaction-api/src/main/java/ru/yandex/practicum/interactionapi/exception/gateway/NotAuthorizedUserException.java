package ru.yandex.practicum.interactionapi.exception.gateway;

/**
 * Исключение, выбрасываемое сервисом, если к ресурсам пытается получить доступ неавторизованный пользователь.
 */
public class NotAuthorizedUserException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение, содержащее подробности исключения.
     */
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}
