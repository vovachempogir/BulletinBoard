package com.example.bulletinboard.exception;

/**
 * Исключение <i>UserNotFoundException</i> является подклассом <i>RuntimeException</i>.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Выбрасывает исключение с сообщением.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
