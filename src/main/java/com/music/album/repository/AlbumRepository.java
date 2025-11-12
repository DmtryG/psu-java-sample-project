package com.music.album.repository;

import com.music.album.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// создаем репозиторий для работы с альбомами в базе данных
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    // поиск альбомов по исполнителю
    List<Album> findByArtistContainingIgnoreCase(String artist);

    // поиск альбомов по названию
    List<Album> findByTitleContainingIgnoreCase(String title);

    // поиск альбомов по жанру
    List<Album> findByGenreContainingIgnoreCase(String genre);

    // поиск альбомов по году релиза
    List<Album> findByReleaseYear(Integer year);
}
