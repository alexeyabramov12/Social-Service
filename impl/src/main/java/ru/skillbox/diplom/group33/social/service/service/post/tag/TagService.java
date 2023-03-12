package ru.skillbox.diplom.group33.social.service.service.post.tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.mapper.post.tag.TagMapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;
import ru.skillbox.diplom.group33.social.service.repository.post.tag.TagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;
    private final TagMapper mapper;

    public Set<Tag> create(Post post, Set<String> tags) {
        Set<Tag> tagSet = new HashSet<>();
        Set<String> validTags = identifyDuplicates(post, tags, tagSet);
        validTags.forEach(t -> {
            Tag tag = new Tag();
            Set<Post> postSet = new HashSet<>();
            postSet.add(post);
            tag.setPosts(postSet);
            tag.setName(t.trim());
            tag.setIsDeleted(false);
            tagSet.add(tag);
            repository.save(tag);
            log.info("in TagService create - create tag: {}", tag);
        });
        return tagSet;
    }

    public Set<Tag> update(Post post, Set<Tag> tagSet, Set<String> nameTagSet) {
        Set<String> originNameTagSet = mapper.convertSetTagsToSetString(tagSet);
        Set<String> creatTagName = getCreatTagName(originNameTagSet, nameTagSet);
        Set<String> deleteTagName = getDeleteTagName(originNameTagSet, nameTagSet);
        tagSet.addAll(create(post, creatTagName));
        delete(post, deleteTagName, tagSet);
        return tagSet;
    }

    private Set<Tag> delete(Post post, Set<String> tags, Set<Tag> tagSet) {
        List<Tag> listTags = repository.findByNameIn(tags);
        listTags.forEach(t -> {
            Set<Post> posts = t.getPosts();
            posts.remove(post);
            t.setPosts(posts);
            tagSet.remove(t);
            repository.save(t);
        });
        return tagSet;
    }

    private Set<String> identifyDuplicates(Post post, Set<String> tags, Set<Tag> tagSet) {
        List<Tag> listTags = repository.findByNameIn(tags);
        listTags.forEach(t -> {
            if (tags.contains(t.getName())) {
                Set<Post> posts = t.getPosts();
                posts.add(post);
                t.setPosts(posts);
                tags.remove(t.getName());
                tagSet.add(t);
                repository.save(t);
                log.info("in TagService identifyDuplicates - tag: {}", t);
            }
        });
        return tags;
    }

    private Set<String> getCreatTagName(Set<String> originNameTagSet, Set<String> nameTagSet) {
        Set<String> creatTagName = new HashSet<>();
        nameTagSet.forEach(t -> {
            if (!originNameTagSet.contains(t.trim())) {
                creatTagName.add(t);
                log.info("in TagService getCreatTagName - add tag with name: {}", t);
            }
        });
        return creatTagName;
    }

    private Set<String> getDeleteTagName(Set<String> originNameTagSet, Set<String> nameTagSet) {
        Set<String> deleteTagName = new HashSet<>();
        originNameTagSet.forEach(t -> {
            if (!nameTagSet.contains(t)) {
                deleteTagName.add(t);
                log.info("in TagService getDeleteTagName - deleted tag with name: {}", t);
            }
        });
        return deleteTagName;
    }

}
