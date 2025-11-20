package com.music.album.service;

import com.music.album.entity.Album;
import com.music.album.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// сервис для работы с музыкальными альбомами
@Service
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    // получаем все альбомы
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    // получаем альбомв по id
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    // сохраняем новый альбом
    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    // обновляем уже существующий альбом
    public Album updateAlbum(Long id, Album albumDetails) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден с ID: " + id));

        album.setTitle(albumDetails.getTitle());
        album.setArtist(albumDetails.getArtist());
        album.setReleaseYear(albumDetails.getReleaseYear());
        album.setGenre(albumDetails.getGenre());
        album.setTrackCount(albumDetails.getTrackCount());
        album.setDescription(albumDetails.getDescription());

        return albumRepository.save(album);
    }

    // удаляем альбом
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    // поиск альбомов по исполнителю
    public List<Album> searchByArtist(String artist) {
        return albumRepository.findByArtistContainingIgnoreCase(artist);
    }

    // поиск альбомов по названию
    public List<Album> searchByTitle(String title) {
        return albumRepository.findByTitleContainingIgnoreCase(title);
    }

    // поиск альбомов по жанру
    public List<Album> searchByGenre(String genre) {
        return albumRepository.findByGenreContainingIgnoreCase(genre);
    }

    // поиск альбомов по году релиза
    public List<Album> searchByYear(Integer year) {
        return albumRepository.findByReleaseYear(year);
    }

    // получить общее количество альбомов
    public long getAlbumCount() {
        return albumRepository.count();
    }
}
