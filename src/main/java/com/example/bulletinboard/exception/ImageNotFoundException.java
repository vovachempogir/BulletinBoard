package com.example.bulletinboard.exception;

/**
 * Исключение <i>UserNotFoundException</i> является подклассом <i>NotFoundException</i>.
 */
public class ImageNotFoundException extends NotFoundException {

    /**
     * Выбрасывает исключение с сообщением <i>"Изображение не найдено"</i>
     */
    public ImageNotFoundException() {
        super("Изображение не найдено");
    }
}
