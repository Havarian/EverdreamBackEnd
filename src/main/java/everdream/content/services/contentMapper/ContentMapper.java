package everdream.content.services.contentMapper;

import everdream.content.controllers.dtos.*;
import everdream.content.dataBase.entities.author.Author;
import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.entities.page.Audio;
import everdream.content.dataBase.entities.page.Page;
import everdream.content.dataBase.entities.page.Video;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface ContentMapper {

    static Book fromBookDto(BookDto bookDto) {

        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setCoverImageName(bookDto.getCoverImageName());
        book.setInCreation(bookDto.isInCreation());
        book.setPublished(bookDto.isPublished());
        book.setCreatorId(bookDto.getCreatorId());
        return book;
    }

    static BookDto toBookDto(Book book) {

        Set<PageDto> pages = new HashSet<>();

        book.getPageTree().forEach(pageTree -> {
            PageDto pageDto = ContentMapper.toPageDto(pageTree.getCurrentPage());
            if (pageTree.getParentPage() != null) {
                pageDto.setParentId(pageTree.getParentPage().getId());
            }
            pages.add(pageDto);
        });

        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .coverImageName(book.getCoverImageName())
                .inCreation(book.isInCreation())
                .creatorId(book.getCreatorId())
                .authors(book.getBookAuthorType().stream().map(bookAuthorType -> {
                            AuthorDto authorDto = toAuthorDto(bookAuthorType.getAuthor());
                            authorDto.setType(bookAuthorType.getAuthorType().getType().name());
                            return authorDto;
                        })
                        .collect(Collectors.toSet()))
                .pages(pages)
                .build();
    }

    static Page fromPageDto(PageDto pageDto) {
        Page page = new Page();
        page.setId(pageDto.getId());
        page.setCreatorId(pageDto.getCreatorId());
        page.setText(pageDto.getText());
        page.setVideo(page.getVideo() != null ? fromVideoDto(pageDto.getVideo()) : new Video());
        page.setAudios(pageDto.getAudio() != null ? pageDto.getAudio().stream()
                .map(ContentMapper::fromAudioDto).collect(Collectors.toSet()) : page.getAudios());
        return page;
    }

    static PageDto toPageDto(Page page) {
        return PageDto.builder()
                .id(page.getId())
                .creatorId(page.getCreatorId())
                .text(page.getText())
                .video(ContentMapper.toVideoDto(page.getVideo()))
                .audio(page.getAudios().stream()
                        .map(ContentMapper::toAudioDto).collect(Collectors.toSet()))
                .build();
    }

    static Audio fromAudioDto(AudioDto audioDto) {
        Audio audio = new Audio();
        audio.setId(audioDto.getId());
        audio.setFileName(audioDto.getFileName());
        return audio;
    }

    static AudioDto toAudioDto(Audio audio) {
        return AudioDto.builder()
                .id(audio.getId())
                .fileName(audio.getFileName())
                .build();
    }

    static Video fromVideoDto(VideoDto videoDto) {
        Video video = new Video();
        video.setId(videoDto.getId());
        video.setFileName(videoDto.getFileName());
        video.setLoopStart(videoDto.getLoopStart());
        video.setLoopEnd(videoDto.getLoopEnd());
        return video;
    }

    static VideoDto toVideoDto(Video video) {
        return VideoDto.builder()
                .id(video.getId())
                .fileName(video.getFileName())
                .loopStart(video.getLoopStart())
                .loopEnd(video.getLoopEnd())
                .build();
    }

    static Author fromAuthorDto(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        author.setEmail(authorDto.getEmail());
        author.setDescription(authorDto.getDescription());
        author.setProfilePictureName(authorDto.getProfilePictureName());
        author.setHomePageUrl(authorDto.getHomePageUrl());

        return author;
    }

    static AuthorDto toAuthorDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .email(author.getEmail())
                .description(author.getDescription())
                .profilePictureName(author.getProfilePictureName())
                .homePageUrl(author.getHomePageUrl())
                .creatorId(author.getCreatorId())
                .build();
    }
}
