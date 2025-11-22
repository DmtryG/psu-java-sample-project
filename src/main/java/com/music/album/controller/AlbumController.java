package com.music.album.controller;

import com.music.album.entity.Album;
import com.music.album.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// контроллер для управления музыкальными альбомами
@Controller
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // главная страница - список всех альбомов
    @GetMapping
    public String listAlbums(Model model) {
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("albums", albums);
        model.addAttribute("albumCount", albums.size());
        return "albums/list";
    }

    // страница добавления нового альбома
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("album", new Album());
        model.addAttribute("action", "create");
        return "albums/form";
    }

    // обработка создания нового альбома
    @PostMapping
    public String createAlbum(@Valid @ModelAttribute("album") Album album,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "albums/form";
        }

        albumService.saveAlbum(album);
        redirectAttributes.addFlashAttribute("successMessage",
                "Альбом " + album.getTitle() + " успешно добавлен");
        return "redirect:/albums";
    }

    // страница редактирования альбома
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Album album = albumService.getAlbumById(id)
                .orElse(null);

        if (album == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Альбом не найден");
            return "redirect:/albums";
        }

        model.addAttribute("album", album);
        model.addAttribute("action", "edit");
        return "albums/form";
    }

    // обработка обновления альбома
    @PostMapping("/update/{id}")
    public String updateAlbum(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("album") Album album,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "edit");
            album.setId(id);
            return "albums/form";
        }

        try {
            albumService.updateAlbum(id, album);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Альбом " + album.getTitle() + " успешно обновлен");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/albums";
    }

    // удаление альбома
    @GetMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Album album = albumService.getAlbumById(id).orElse(null);
            if (album != null) {
                albumService.deleteAlbum(id);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Альбом " + album.getTitle() + " успешно удален");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Альбом не найден");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении альбома: " + e.getMessage());
        }

        return "redirect:/albums";
    }

    // просмотр деталей альбома
    @GetMapping("/view/{id}")
    public String viewAlbum(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Album album = albumService.getAlbumById(id).orElse(null);

        if (album == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Альбом не найден");
            return "redirect:/albums";
        }

        model.addAttribute("album", album);
        return "albums/view";
    }

    // поиск альбомов
    @GetMapping("/search")
    public String searchAlbums(@RequestParam(required = false) String searchType,
                               @RequestParam(required = false) String searchQuery,
                               Model model) {
        List<Album> albums;

        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            albums = albumService.getAllAlbums();
        } else {
            switch (searchType != null ? searchType : "title") {
                case "artist":
                    albums = albumService.searchByArtist(searchQuery);
                    break;
                case "genre":
                    albums = albumService.searchByGenre(searchQuery);
                    break;
                case "year":
                    try {
                        Integer year = Integer.parseInt(searchQuery);
                        albums = albumService.searchByYear(year);
                    } catch (NumberFormatException e) {
                        albums = List.of();
                    }
                    break;
                default:
                    albums = albumService.searchByTitle(searchQuery);
                    break;
            }
        }

        model.addAttribute("albums", albums);
        model.addAttribute("albumCount", albums.size());
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchQuery", searchQuery);
        return "albums/list";
    }
}
