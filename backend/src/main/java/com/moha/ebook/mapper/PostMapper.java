package com.moha.ebook.mapper;

import com.moha.ebook.dto.PostDto;
import com.moha.ebook.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto toPostDto(Post post) {
        PostDto postDto = PostDto.builder()
                .idPost(post.getIdPost())
                .contenu(post.getContenu())
                .datePost(post.getDatePost())
                .idUtilisateur(post.getUtilisateur().getIdUtilisateur())
                .idLivre(post.getLivre().getIdLivre())
                .build();
        return postDto;
    }

    public Post toPost(PostDto postDto) {
        Post post = Post.builder()
                .idPost(postDto.getIdPost())
                .contenu(postDto.getContenu())
                .datePost(postDto.getDatePost())
                .build();
        return post;
    }

}
