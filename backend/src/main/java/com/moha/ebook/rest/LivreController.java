package com.moha.ebook.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moha.ebook.dto.LivreDto;
import com.moha.ebook.entities.Livre;
import com.moha.ebook.services.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<Livre> getLivres() {
        return livreService.getLivres();
    }

    @GetMapping("/{id}")
    public LivreDto getLivre(@PathVariable("id") Long id) {
        return livreService.getLivre(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public LivreDto saveLivre(@RequestParam("image") MultipartFile image,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("dto") String dto) throws IOException {
        LivreDto livreDto = objectMapper.readValue(dto, LivreDto.class);
        return livreService.saveLivre(livreDto, image, file);
    }

    @PutMapping(consumes = {"multipart/form-data"})
    public LivreDto updateLivre(@RequestParam("image") MultipartFile image,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("dto") String dto) throws IOException {
        LivreDto livreDto = objectMapper.readValue(dto, LivreDto.class);
        return livreService.updateLivre(livreDto, image, file);
    }

}
