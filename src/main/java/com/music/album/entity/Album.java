package com.music.album.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// создаем сущность музыкального альбома
@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название альбома не может быть пустым")
    @Size(min = 1, max = 200, message = "Название должно быть от 1 до 200 символов")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Имя исполнителя не может быть пустым")
    @Size(min = 1, max = 200, message = "Имя исполнителя должно быть от 1 до 150 символов")
    @Column(name = "artist", nullable = false, length = 150)
    private String artist;

    @NotNull(message = "Год выпуска обязателен")
    @Min(value = 1900, message = "Год выпуска должен быть не раньше 1900")
    @Max(value = 2100, message = "Год выпуска должен быть не позже 2100")
    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @NotNull(message = "Жанр не может быть пустым")
    @Size(min = 1, max = 100, message = "Жанр должен быть от 1 до 100 символов")
    @Column(name = "genre", nullable = false, length = 100)
    private String genre;

    @Min(value = 1, message = "Количество треков должно быть не менее 1")
    @Max(value = 999, message = "Количество треков не может превышать 999")
    @Column(name = "track_count")
    private Integer trackCount;

    @Size(max = 500, message = "Описание не должно превышать 500 символов")
    @Column(name = "description", length = 500)
    private String description;

    // конструкторы
    public Album() {
    }

    public Album (String title, String artist, Integer releaseYear, String genre, Integer trackCount, String description) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.trackCount = trackCount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                ", trackCount=" + trackCount +
                ", description='" + description + '\'' +
                '}';
    }
}
