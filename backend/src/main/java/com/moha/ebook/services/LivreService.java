package com.moha.ebook.services;

import com.moha.ebook.dao.LivreRepository;
import com.moha.ebook.dao.UtilisateurRepository;
import com.moha.ebook.dto.LivreDto;
import com.moha.ebook.entities.Livre;
import com.moha.ebook.entities.Utilisateur;
import com.moha.ebook.mapper.LivreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LivreService {

    private final LivreRepository livreRepository;
    private final LivreMapper livreMapper;
    private final UtilisateurRepository utilisateurRepository;

    public LivreDto saveLivre(LivreDto livreDto, MultipartFile image, MultipartFile file) throws IOException {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(livreDto.getIdUtilisateur());

        if (optionalUtilisateur.isPresent()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String imageName = StringUtils.cleanPath(image.getOriginalFilename());

            Path ebookFiles = Paths.get("D:\\", "ebbok_files");
            Path ebookImages = Paths.get("D:\\", "ebook_images");

            if (!Files.exists(ebookFiles)) {
                Files.createDirectory(ebookFiles);
            }

            if (!Files.exists(ebookImages)) {
                Files.createDirectory(ebookImages);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = ebookFiles.resolve(fileName);
                livreDto.setPath(filePath.toString());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not store file " + fileName + ". Please try again!", e);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Path imagePath = ebookImages.resolve(imageName);
                livreDto.setImage(imagePath.toString());
                Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not store image " + imageName + ". Please try again!", e);
            }

            Utilisateur utilisateur = optionalUtilisateur.get();

            Livre livre = livreMapper.toLivre(livreDto);
            livre.setUtilisateur(utilisateur);
            livre = livreRepository.save(livre);

            return livreMapper.toLivreDto(livre);
        }
        throw new RuntimeException("Utilisateur not found");
    }

    public LivreDto getLivre(Long id) {
        Optional<Livre> optionalLivre = livreRepository.findById(id);
        if (optionalLivre.isPresent()) {
            return livreMapper.toLivreDto(optionalLivre.get());
        }
        throw new RuntimeException("Livre not found");
    }

    public List<Livre> getLivres() {
        return livreRepository.findAll();
    }

    public LivreDto updateLivre(LivreDto livreDto, MultipartFile image, MultipartFile file) throws IOException {
        Optional<Livre> optionalLivre = livreRepository.findById(livreDto.getIdLivre());
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(livreDto.getIdUtilisateur());
        if (optionalLivre.isPresent() && optionalUtilisateur.isPresent()) {
            Livre livre = optionalLivre.get();

            if (file != null && !livre.getPath().contains(file.getOriginalFilename())) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path ebookFiles = Paths.get("D:\\", "ebbok_files");
                if (!Files.exists(ebookFiles)) {
                    Files.createDirectory(ebookFiles);
                }
                try (InputStream inputStream = file.getInputStream()) {
                    if (livre.getPath() != null) {
                        Files.deleteIfExists(Paths.get(livre.getPath()));
                    }
                    Path filePath = ebookFiles.resolve(fileName);
                    livreDto.setPath(filePath.toString());
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new IOException("Could not store file " + fileName + ". Please try again!", e);
                }
            }
            if (image != null && !livre.getImage().contains(image.getOriginalFilename())) {
                String imageName = StringUtils.cleanPath(image.getOriginalFilename());
                Path ebookImages = Paths.get("D:\\", "ebook_images");
                if (!Files.exists(ebookImages)) {
                    Files.createDirectory(ebookImages);
                }
                try (InputStream inputStream = image.getInputStream()) {
                    if (livre.getImage() != null) {
                        Files.deleteIfExists(Paths.get(livre.getImage()));
                    }
                    Path imagePath = ebookImages.resolve(imageName);
                    livreDto.setImage(imagePath.toString());
                    Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            livre = livreMapper.toLivre(livreDto);
            livre.setUtilisateur(optionalUtilisateur.get());
            livre = livreRepository.save(livre);
            return livreMapper.toLivreDto(livre);
        }
        throw new RuntimeException("Livre not found");
    }

    public void deleteLivre(Long id) throws Exception {
        Optional<Livre> optionalLivre = livreRepository.findById(id);
        if (optionalLivre.isPresent()) {
            Livre livre = optionalLivre.get();
            if (livre.getPath() != null) {
                try {
                    Files.deleteIfExists(Paths.get(livre.getPath()));
                } catch (IOException e) {
                    throw new Exception(e);
                }
            }
            if (livre.getImage() != null) {
                try {
                    Files.deleteIfExists(Paths.get(livre.getImage()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            livreRepository.deleteById(id);
        } else
            throw new RuntimeException("Livre not found");
    }

}
