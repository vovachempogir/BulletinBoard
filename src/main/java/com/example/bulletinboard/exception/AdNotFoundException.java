package com.example.bulletinboard.exception;


/**
 * Исключение <i>UserNotFoundException</i> является подклассом <i>NotFoundException</i>.
 */
public class AdNotFoundException extends NotFoundException{

    /**
     * Выбрасывает исключение с сообщением <i>"Объявление не найдено"</i>
     */
    public AdNotFoundException() {
        super("Объявление не найдено");
    }
}
