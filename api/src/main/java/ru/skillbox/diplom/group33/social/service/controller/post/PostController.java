package ru.skillbox.diplom.group33.social.service.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group33.social.service.controller.base.BaseController;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@Tag(name="Система управления постами")
@RequestMapping(PathConstant.URL + "post/")
public interface PostController extends BaseController<PostDto, PostSearchDto> {

    @Override
    @GetMapping("{id}")
    @Operation(summary = "Получение поста")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<PostDto> getById(@PathVariable Long id);

    @Override
    @GetMapping()
    @Operation(summary = "Получение всей информации о посте")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<Page<PostDto>> getAll(PostSearchDto searchDto, Pageable page);

    @Override
    @PostMapping()
    @Operation(summary = "Создание поста")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное создание", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<PostDto> create(@RequestBody PostDto dto);

    @Override
    @PutMapping()
    @Operation(summary = "Обновление поста")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное обновление", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<PostDto> update(@RequestBody PostDto dto);

    @Override
    @DeleteMapping(value = "{id}")
    @Operation(summary = "Удаление поста")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное удаление", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
        @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity deleteById(@PathVariable Long id);

}
