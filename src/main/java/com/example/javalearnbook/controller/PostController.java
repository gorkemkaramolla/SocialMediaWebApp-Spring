package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.PostDto;
import com.example.javalearnbook.model.Post;

import com.example.javalearnbook.service.PostService;
import com.example.javalearnbook.service.WriterService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService, WriterService writerService) {
        this.postService = postService;
    }

    @GetMapping
    private List<PostDto> getAllPosts(@RequestParam Optional<Long> writerId)
    {

        return convertToDtoList(postService.getWritersPosts(writerId));
    }
    @GetMapping("/{postId}")
    private PostDto getPostById(@PathVariable Long postId)
    {
        return  convertToDto(postService.getPostById(postId));
    }
    @PostMapping
    private PostDto createPost(@RequestBody PostDto postDto)
    {
        Post post = convertToEntity(postDto);
        Post responsePost = postService.createPost(postDto.getWriterId(), post);
        return convertToDto(responsePost);

    }
    @PutMapping("{postId}")
    public PostDto changePostInfo(@PathVariable Long postId,@RequestBody PostDto postDto)
    {
        Post changedPost= convertToEntity(postDto);
        return convertToDto(postService.changePostInfo(postId,changedPost));
    }
    @DeleteMapping("{postId}")
    public String deletePost(@PathVariable Long postId)
    {
        return postService.deletePost(postId);
    }







    @Bean
    public ModelMapper modelMapper1()
    {
        return new ModelMapper();
    }
    private final ModelMapper modelMapper = modelMapper1();
    /*private BlogPostDto convertDtoskipField(BlogPost blogPost)
    {
        modelMapper.addMappings(new PropertyMap<BlogPost, BlogPostDto>() {
            @Override
            protected void configure()
            {
                skip(destination.getWriterId());
            }

        });
        return modelMapper.map(blogPost, BlogPostDto.class);

    }*/

    private PostDto convertToDto(Post post)
    {
        return modelMapper.map(post,PostDto.class);
    }
    private List<PostDto> convertToDtoList(List<Post> posts)
    {
        return posts.stream().map((blogPost) -> modelMapper.map(blogPost,PostDto.class)).toList();
    }
    private Post convertToEntity(PostDto postDto)
    {
        return modelMapper.map(postDto, Post.class);
    }
}

























