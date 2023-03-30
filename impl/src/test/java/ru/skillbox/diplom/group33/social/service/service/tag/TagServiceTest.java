package ru.skillbox.diplom.group33.social.service.service.tag;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

   /* @Mock
    private TagRepository mockRepository;
    @Mock
    private TagMapper mockMapper;

    private TagService tagServiceUnderTest;

    @BeforeEach
    void setUp() {
        tagServiceUnderTest = new TagService(mockRepository, mockMapper);
    }

    @Test
    void testCreate() {
        // Setup
        final Post post = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));

        // Configure TagRepository.findByNameIn(...).
        final List<Tag> tags = List.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockRepository.findByNameIn(Set.of("value"))).thenReturn(tags);

        // Configure TagRepository.save(...).
        final Tag tag = new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC))));
        when(mockRepository.save(any(Tag.class))).thenReturn(tag);

        // Run the test
        final Set<Tag> result = tagServiceUnderTest.create(post, Set.of("value"));

        // Verify the results
        verify(mockRepository).save(any(Tag.class));
    }

    @Test
    void testCreate_TagRepositoryFindByNameInReturnsNoItems() {
        // Setup
        final Post post = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        when(mockRepository.findByNameIn(Set.of("value"))).thenReturn(Collections.emptyList());

        // Configure TagRepository.save(...).
        final Tag tag = new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC))));
        when(mockRepository.save(any(Tag.class))).thenReturn(tag);

        // Run the test
        final Set<Tag> result = tagServiceUnderTest.create(post, Set.of("value"));

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptySet());
        verify(mockRepository).save(any(Tag.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final Post post = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        final Set<Tag> tagSet = Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockMapper.convertSetTagsToSetString(Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))))))
                .thenReturn(Set.of("value"));

        // Configure TagRepository.findByNameIn(...).
        final List<Tag> tags = List.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockRepository.findByNameIn(Set.of("value"))).thenReturn(tags);

        // Configure TagRepository.save(...).
        final Tag tag = new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC))));
        when(mockRepository.save(any(Tag.class))).thenReturn(tag);

        // Run the test
        final Set<Tag> result = tagServiceUnderTest.update(post, tagSet, Set.of("value"));

        // Verify the results
        verify(mockRepository).save(any(Tag.class));
    }

    @Test
    void testUpdate_TagMapperReturnsNoItems() {
        // Setup
        final Post post = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        final Set<Tag> tagSet = Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockMapper.convertSetTagsToSetString(Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))))))
                .thenReturn(Collections.emptySet());

        // Configure TagRepository.findByNameIn(...).
        final List<Tag> tags = List.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockRepository.findByNameIn(Set.of("value"))).thenReturn(tags);

        // Configure TagRepository.save(...).
        final Tag tag = new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC))));
        when(mockRepository.save(any(Tag.class))).thenReturn(tag);

        // Run the test
        final Set<Tag> result = tagServiceUnderTest.update(post, tagSet, Set.of("value"));

        // Verify the results
        verify(mockRepository).save(any(Tag.class));
    }

    @Test
    void testUpdate_TagRepositoryFindByNameInReturnsNoItems() {
        // Setup
        final Post post = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        final Set<Tag> tagSet = Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockMapper.convertSetTagsToSetString(Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))))))
                .thenReturn(Set.of("value"));
        when(mockRepository.findByNameIn(Set.of("value"))).thenReturn(Collections.emptyList());

        // Configure TagRepository.save(...).
        final Tag tag = new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC))));
        when(mockRepository.save(any(Tag.class))).thenReturn(tag);

//        // Run the test
//        final Set<Tag> result = tagServiceUnderTest.update(post, tagSet, Set.of("value"));
//
//        // Verify the results
//        assertThat(result).isEqualTo(Collections.emptySet());
//        verify(mockRepository).save(any(Tag.class));
    }

    @Test
    void testIdentifyDuplicates() {
        // Setup
        final Post post = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        final Set<Tag> tagSet = Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));

        // Configure TagRepository.findByNameIn(...).
        final List<Tag> tags = List.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockRepository.findByNameIn(Set.of("value"))).thenReturn(tags);

        // Configure TagRepository.save(...).
        final Tag tag = new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC))));
        when(mockRepository.save(any(Tag.class))).thenReturn(tag);

        // Run the test
        final Set<String> result = tagServiceUnderTest.identifyDuplicates(post, Set.of("value"), tagSet);

        // Verify the results
        assertThat(result).isEqualTo(Set.of("value"));
        verify(mockRepository).save(any(Tag.class));
    }

    @Test
    void testIdentifyDuplicates_TagRepositoryFindByNameInReturnsNoItems() {
        // Setup
        final Post post = new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title", PostType.POSTED,
                "postText", false, 0, Set.of(new Tag("name", Set.of())), 0, false, "imagePath",
                ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC));
        final Set<Tag> tagSet = Set.of(new Tag("name",
                Set.of(new Post(ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC),
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC), 0L, "title",
                        PostType.POSTED, "postText", false, 0, Set.of(), 0, false, "imagePath",
                        ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0), ZoneOffset.UTC)))));
        when(mockRepository.findByNameIn(Set.of("value"))).thenReturn(Collections.emptyList());

        // Run the test
        final Set<String> result = tagServiceUnderTest.identifyDuplicates(post, Set.of("value"), tagSet);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptySet());
    }*/
}
