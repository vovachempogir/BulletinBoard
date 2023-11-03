package com.example.bulletinboard.exception;

/**
 * Исключение <i>UserNotFoundException</i> является подклассом <i>NotFoundException</i>.
 */
public class UserNotFoundException extends NotFoundException{

    /**
     * Выбрасывает исключение с сообщением <i>"Пользователь не найден"</i>
     */
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
