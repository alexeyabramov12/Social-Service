package ru.skillbox.diplom.group33.social.service.controller.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group33.social.service.dto.storage.StorageDto;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;
import ru.skillbox.diplom.group33.social.service.service.storage.StorageService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StorageControllerImpl implements StorageController {

    private final StorageService storageService;
    private final AccountService accountService;


    @Override
    public ResponseEntity<StorageDto> upload(@RequestParam("file")
                                             MultipartFile file) throws IOException {
        StorageDto uploadResult = storageService.uploadFile(file);
        accountService.updateAccountPhoto(uploadResult);
        return ResponseEntity.status(HttpStatus.OK).body(uploadResult);
    }


}

