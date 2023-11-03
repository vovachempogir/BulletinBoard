package com.example.bulletinboard.service.impl;
import com.example.bulletinboard.exception.ImageNotFoundException;
import com.example.bulletinboard.repository.ImageRepo;
import com.example.bulletinboard.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.bulletinboard.entity.Image;
import java.io.IOException;

/**
 * Реализация интерфейса ImageService, предоставляющая функциональность загрузки и получения изображений.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;

    /**
     * Загружает изображение и сохраняет его в базе данных.
     *
     * @param imageFile мультифайл изображения для загрузки
     * @return объект Image, представляющий сохраненное изображение
     * @throws IOException если возникает ошибка ввода-вывода при загрузке изображения
     */
    @Override
    public Image upload(MultipartFile imageFile) throws IOException {
        Image image = new Image();
        image.setData(imageFile.getBytes());
        log.debug("Image was saved");
        return imageRepo.save(image);
    }

    /**
     * Возвращает данные изображения по заданному идентификатору.
     *
     * @param imageId идентификатор изображения
     * @return массив байтов, представляющих данные изображения
     * @throws ImageNotFoundException если изображение не найдено
     */
    @Override
    @Transactional
    public byte[] getImage(Integer imageId){
        Image image = imageRepo.findById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }

}
