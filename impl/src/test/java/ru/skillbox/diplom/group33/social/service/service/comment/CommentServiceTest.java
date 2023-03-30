package ru.skillbox.diplom.group33.social.service.service.comment;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

   /* @Mock
    private CommentRepository mockCommentRepository;
    @Mock
    private PostRepository mockPostRepository;
    @Mock
    private CommentMapper mockMapper;
    @Mock
    private LikeService mockLikeService;

    private CommentService commentServiceUnderTest;

    @BeforeEach
    void setUp() {
        commentServiceUnderTest = new CommentService(mockCommentRepository, mockPostRepository, mockMapper,
                mockLikeService);
    }

    @Test
    void testGetAll() {
        // Setup
        // Configure CommentRepository.findAll(...).
        final Page<Comment> comments = new PageImpl<>(List.of(new Comment(CommentType.POST,
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText", 0L,
                false, 0, false, 0, "imagePath")));
        when(mockCommentRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(comments);

        // Configure CommentMapper.convertToDto(...).
        final CommentDto commentDto = new CommentDto();
        commentDto.setId(0L);
        commentDto.setCommentType(CommentType.POST);
        commentDto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setAuthorId(0L);
        commentDto.setParentId(0L);
        commentDto.setCommentText("commentText");
        commentDto.setPostId(0L);
        commentDto.setIsBlocked(false);
        commentDto.setLikeAmount(0);
        commentDto.setMyLike(false);
        commentDto.setCommentsCount(0);
        commentDto.setImagePath("imagePath");
        when(mockMapper.convertToDto(any(Comment.class))).thenReturn(commentDto);

        when(mockLikeService.getMyLike(0L, LikeType.COMMENT)).thenReturn(false);

        // Run the test
        final Page<CommentDto> result = commentServiceUnderTest.getAll(0L, PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testGetAll_CommentRepositoryReturnsNoItems() {
        // Setup
        when(mockCommentRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Configure CommentMapper.convertToDto(...).
        final CommentDto commentDto = new CommentDto();
        commentDto.setId(0L);
        commentDto.setCommentType(CommentType.POST);
        commentDto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setAuthorId(0L);
        commentDto.setParentId(0L);
        commentDto.setCommentText("commentText");
        commentDto.setPostId(0L);
        commentDto.setIsBlocked(false);
        commentDto.setLikeAmount(0);
        commentDto.setMyLike(false);
        commentDto.setCommentsCount(0);
        commentDto.setImagePath("imagePath");
        when(mockMapper.convertToDto(any(Comment.class))).thenReturn(commentDto);

        when(mockLikeService.getMyLike(0L, LikeType.COMMENT)).thenReturn(false);

        // Run the test
        final Page<CommentDto> result = commentServiceUnderTest.getAll(0L, PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testCreate() {
        // Setup
        final CommentDto dto = new CommentDto();
        dto.setId(0L);
        dto.setCommentType(CommentType.POST);
        dto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setAuthorId(0L);
        dto.setParentId(0L);
        dto.setCommentText("commentText");
        dto.setPostId(0L);
        dto.setIsBlocked(false);
        dto.setLikeAmount(0);
        dto.setMyLike(false);
        dto.setCommentsCount(0);
        dto.setImagePath("imagePath");

        // Configure PostRepository.findById(...).
        final Optional<Post> post = Optional.of(
                new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)));
        when(mockPostRepository.findById(0L)).thenReturn(post);

        // Configure PostRepository.save(...).
        final Post post1 = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        when(mockPostRepository.save(any(Post.class))).thenReturn(post1);

        // Configure CommentRepository.findById(...).
        final Optional<Comment> comment = Optional.of(
                new Comment(CommentType.POST, ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText",
                        0L, false, 0, false, 0, "imagePath"));
        when(mockCommentRepository.findById(0L)).thenReturn(comment);

        // Configure CommentRepository.save(...).
        final Comment comment1 = new Comment(CommentType.POST,
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText", 0L,
                false, 0, false, 0, "imagePath");
        when(mockCommentRepository.save(any(Comment.class))).thenReturn(comment1);

        // Configure CommentMapper.initEntity(...).
        final Comment comment2 = new Comment(CommentType.POST,
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText", 0L,
                false, 0, false, 0, "imagePath");
        when(mockMapper.initEntity(any(CommentDto.class))).thenReturn(comment2);

        // Configure CommentMapper.convertToDto(...).
        final CommentDto commentDto = new CommentDto();
        commentDto.setId(0L);
        commentDto.setCommentType(CommentType.POST);
        commentDto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setAuthorId(0L);
        commentDto.setParentId(0L);
        commentDto.setCommentText("commentText");
        commentDto.setPostId(0L);
        commentDto.setIsBlocked(false);
        commentDto.setLikeAmount(0);
        commentDto.setMyLike(false);
        commentDto.setCommentsCount(0);
        commentDto.setImagePath("imagePath");
        when(mockMapper.convertToDto(any(Comment.class))).thenReturn(commentDto);

        // Run the test
        final CommentDto result = commentServiceUnderTest.create(0L, dto);

        // Verify the results
        verify(mockPostRepository).save(any(Post.class));
        verify(mockCommentRepository).save(any(Comment.class));
    }

    @Test
    void testCreate_PostRepositoryFindByIdReturnsAbsent() {
        // Setup
        final CommentDto dto = new CommentDto();
        dto.setId(0L);
        dto.setCommentType(CommentType.POST);
        dto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setAuthorId(0L);
        dto.setParentId(0L);
        dto.setCommentText("commentText");
        dto.setPostId(0L);
        dto.setIsBlocked(false);
        dto.setLikeAmount(0);
        dto.setMyLike(false);
        dto.setCommentsCount(0);
        dto.setImagePath("imagePath");

        when(mockPostRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> commentServiceUnderTest.create(0L, dto))
                .isInstanceOf(EntityNotFoundResponseStatusException.class);
    }

    @Test
    void testCreate_CommentRepositoryFindByIdReturnsAbsent() {
        // Setup
        final CommentDto dto = new CommentDto();
        dto.setId(0L);
        dto.setCommentType(CommentType.POST);
        dto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setAuthorId(0L);
        dto.setParentId(0L);
        dto.setCommentText("commentText");
        dto.setPostId(0L);
        dto.setIsBlocked(false);
        dto.setLikeAmount(0);
        dto.setMyLike(false);
        dto.setCommentsCount(0);
        dto.setImagePath("imagePath");

        when(mockCommentRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> commentServiceUnderTest.create(0L, dto))
                .isInstanceOf(EntityNotFoundResponseStatusException.class);
    }

    @Test
    void testUpdate() {
        // Setup
        final CommentDto dto = new CommentDto();
        dto.setId(0L);
        dto.setCommentType(CommentType.POST);
        dto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setAuthorId(0L);
        dto.setParentId(0L);
        dto.setCommentText("commentText");
        dto.setPostId(0L);
        dto.setIsBlocked(false);
        dto.setLikeAmount(0);
        dto.setMyLike(false);
        dto.setCommentsCount(0);
        dto.setImagePath("imagePath");

        // Configure CommentRepository.findById(...).
        final Optional<Comment> comment = Optional.of(
                new Comment(CommentType.POST, ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText",
                        0L, false, 0, false, 0, "imagePath"));
        when(mockCommentRepository.findById(0L)).thenReturn(comment);

        // Configure CommentRepository.save(...).
        final Comment comment1 = new Comment(CommentType.POST,
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText", 0L,
                false, 0, false, 0, "imagePath");
        when(mockCommentRepository.save(any(Comment.class))).thenReturn(comment1);

        // Configure CommentMapper.convertToDto(...).
        final CommentDto commentDto = new CommentDto();
        commentDto.setId(0L);
        commentDto.setCommentType(CommentType.POST);
        commentDto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setAuthorId(0L);
        commentDto.setParentId(0L);
        commentDto.setCommentText("commentText");
        commentDto.setPostId(0L);
        commentDto.setIsBlocked(false);
        commentDto.setLikeAmount(0);
        commentDto.setMyLike(false);
        commentDto.setCommentsCount(0);
        commentDto.setImagePath("imagePath");
        when(mockMapper.convertToDto(any(Comment.class))).thenReturn(commentDto);

        // Run the test
        final CommentDto result = commentServiceUnderTest.update(dto, 0L, 0L);

        // Verify the results
    }

    @Test
    void testUpdate_CommentRepositoryFindByIdReturnsAbsent() {
        // Setup
        final CommentDto dto = new CommentDto();
        dto.setId(0L);
        dto.setCommentType(CommentType.POST);
        dto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        dto.setAuthorId(0L);
        dto.setParentId(0L);
        dto.setCommentText("commentText");
        dto.setPostId(0L);
        dto.setIsBlocked(false);
        dto.setLikeAmount(0);
        dto.setMyLike(false);
        dto.setCommentsCount(0);
        dto.setImagePath("imagePath");

        when(mockCommentRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> commentServiceUnderTest.update(dto, 0L, 0L))
                .isInstanceOf(EntityNotFoundResponseStatusException.class);
    }

    @Test
    void testDeleteById() {
        // Setup
        // Configure CommentRepository.findById(...).
        final Optional<Comment> comment = Optional.of(
                new Comment(CommentType.POST, ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText",
                        0L, false, 0, false, 0, "imagePath"));
        when(mockCommentRepository.findById(0L)).thenReturn(comment);

        // Configure PostRepository.findById(...).
        final Optional<Post> post = Optional.of(
                new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)));
        when(mockPostRepository.findById(0L)).thenReturn(post);

        // Configure PostRepository.save(...).
        final Post post1 = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        when(mockPostRepository.save(any(Post.class))).thenReturn(post1);

        // Configure CommentRepository.save(...).
        final Comment comment1 = new Comment(CommentType.POST,
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText", 0L,
                false, 0, false, 0, "imagePath");
        when(mockCommentRepository.save(any(Comment.class))).thenReturn(comment1);

        // Run the test
        commentServiceUnderTest.deleteById(0L, 0L);

        // Verify the results
        verify(mockPostRepository).save(any(Post.class));
        verify(mockCommentRepository).save(any(Comment.class));
        verify(mockCommentRepository).deleteById(0L);
    }

    @Test
    void testDeleteById_CommentRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockCommentRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> commentServiceUnderTest.deleteById(0L, 0L))
                .isInstanceOf(EntityNotFoundResponseStatusException.class);
    }

    @Test
    void testDeleteById_PostRepositoryFindByIdReturnsAbsent() {
        // Setup
        // Configure CommentRepository.findById(...).
        final Optional<Comment> comment = Optional.of(
                new Comment(CommentType.POST, ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText",
                        0L, false, 0, false, 0, "imagePath"));
        when(mockCommentRepository.findById(0L)).thenReturn(comment);

        when(mockPostRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> commentServiceUnderTest.deleteById(0L, 0L))
                .isInstanceOf(EntityNotFoundResponseStatusException.class);
    }

    @Test
    void testGetAllSubComment() {
        // Setup
        // Configure CommentRepository.findAll(...).
        final Page<Comment> comments = new PageImpl<>(List.of(new Comment(CommentType.POST,
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, 0L, "commentText", 0L,
                false, 0, false, 0, "imagePath")));
        when(mockCommentRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(comments);

        // Configure CommentMapper.convertToDto(...).
        final CommentDto commentDto = new CommentDto();
        commentDto.setId(0L);
        commentDto.setCommentType(CommentType.POST);
        commentDto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setAuthorId(0L);
        commentDto.setParentId(0L);
        commentDto.setCommentText("commentText");
        commentDto.setPostId(0L);
        commentDto.setIsBlocked(false);
        commentDto.setLikeAmount(0);
        commentDto.setMyLike(false);
        commentDto.setCommentsCount(0);
        commentDto.setImagePath("imagePath");
        when(mockMapper.convertToDto(any(Comment.class))).thenReturn(commentDto);

        // Run the test
        final Page<CommentDto> result = commentServiceUnderTest.getAllSubComment(0L, 0L, PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testGetAllSubComment_CommentRepositoryReturnsNoItems() {
        // Setup
        when(mockCommentRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Configure CommentMapper.convertToDto(...).
        final CommentDto commentDto = new CommentDto();
        commentDto.setId(0L);
        commentDto.setCommentType(CommentType.POST);
        commentDto.setTime(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setTimeChanged(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        commentDto.setAuthorId(0L);
        commentDto.setParentId(0L);
        commentDto.setCommentText("commentText");
        commentDto.setPostId(0L);
        commentDto.setIsBlocked(false);
        commentDto.setLikeAmount(0);
        commentDto.setMyLike(false);
        commentDto.setCommentsCount(0);
        commentDto.setImagePath("imagePath");
        when(mockMapper.convertToDto(any(Comment.class))).thenReturn(commentDto);

        // Run the test
        final Page<CommentDto> result = commentServiceUnderTest.getAllSubComment(0L, 0L, PageRequest.of(0, 1));

        // Verify the results
    }*/
}
