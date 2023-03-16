package ru.skillbox.diplom.group33.social.service.service.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group33.social.service.dto.storage.StorageDto;

import java.io.IOException;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {

    private final Cloudinary cloudinaryConfig;


    public StorageDto uploadFile(MultipartFile file) throws IOException {
        Map params = ObjectUtils.asMap("transformation",
                new Transformation()
                        .height(400).width(400).crop("pad"),
                "filename_override", file.getOriginalFilename());
        Map uploadResult = cloudinaryConfig.uploader().upload(file.getBytes(), params);
        StorageDto storageDto = new StorageDto();
        storageDto.setPhotoPath(uploadResult.get("url").toString());
        storageDto.setPhotoName(uploadResult.get("original_filename").toString());
        return storageDto;
    }
}
