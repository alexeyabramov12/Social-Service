package ru.skillbox.diplom.group33.social.service.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.controller.base.BaseController;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@Tag(name = "Система управления постами")
@RequestMapping(PathConstant.URL + "post")
public interface PostController extends BaseController<PostDto, PostSearchDto> {


    //_______________________________________POST__________________________________________________________________


    @Override
    @GetMapping("/{id}")
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

    @PutMapping("/{id}")
    @Operation(summary = "Обновление поста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<PostDto> update(@PathVariable Long id, @RequestBody PostDto dto);

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


    //_______________________________________COMMENT________________________________________________________________


    @GetMapping("/{id}/comment")
    ResponseEntity<Page<CommentDto>> getComments(@PathVariable Long id, Pageable page);

    @PostMapping("/{id}/comment")
    ResponseEntity<CommentDto> createComment(@PathVariable Long id, @RequestBody CommentDto dto);

    @PutMapping("/{postId}/comment/{commentId}")
    ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto dto, @PathVariable Long postId, @PathVariable Long commentId);


    @DeleteMapping("/{postId}/comment/{commentId}")
    ResponseEntity deleteByIdComment(@PathVariable Long postId, @PathVariable Long commentId);

    @GetMapping("/{postId}/comment/{commentId}/subcomment")
    ResponseEntity<Page<CommentDto>> getSubcomment(@PathVariable Long postId, @PathVariable Long commentId, Pageable page);


    //_______________________________________LIKE___________________________________________________________________


    @PostMapping("/{itemId}/like")
    @Operation(summary = "Создание лайка к посту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Лайк к посту создан",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(allOf = {LikeDto.class}))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = ""))})
    ResponseEntity<LikeDto> createPostLike(@PathVariable Long itemId);

    @DeleteMapping(value = "/{itemId}/like")
    ResponseEntity deletePostLike(@PathVariable Long itemId);

    @PostMapping("/{postId}/comment/{itemId}/like")
    ResponseEntity<LikeDto> changeCommentLike(@PathVariable("postId") Long postId, @PathVariable("itemId") Long itemId);

    @DeleteMapping("/{postId}/comment/{itemId}/like")
    ResponseEntity<LikeDto> changeCommentLike1(@PathVariable("postId") Long postId, @PathVariable("itemId") Long itemId);

}
