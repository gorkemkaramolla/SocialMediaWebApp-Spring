package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.BlogPostDto;
import com.example.javalearnbook.model.BlogPost;
import com.example.javalearnbook.model.BlogWriter;
import com.example.javalearnbook.service.BlogPostService;
import com.example.javalearnbook.service.BlogWriterService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class BlogPostController {

    private BlogPostService blogPostService;
    private final BlogWriterService blogWriterService;
    @GetMapping
    private List<BlogPostDto> getAllPosts(@RequestParam Optional<Long> writerId)
    {

        return convertToDtoList(blogPostService.getWritersPosts(writerId));
    }
    @GetMapping("/{postId}")
    private BlogPostDto getPostById(@PathVariable Long postId)
    {
        return  convertToDto(blogPostService.getPostById(postId));
    }
    @PostMapping
    private BlogPostDto createPost(@RequestBody BlogPostDto blogPostDto)
    {
        BlogWriter writer = blogWriterService.getOneWriter(blogPostDto.getWriterId());
        if(writer != null)
        {
            BlogPost blogPost = convertToEntity(blogPostDto);
            return convertToDto(blogPostService.createPost(blogPost));
        }
        return null;
    }

    @Bean
    public ModelMapper modelMapper1()
    {
        return new ModelMapper();
    }
    private final ModelMapper modelMapper = modelMapper1();

    private BlogPostDto convertToDto(BlogPost blogPost)
    {
        return modelMapper.map(blogPost,BlogPostDto.class);
    }
    private List<BlogPostDto> convertToDtoList(List<BlogPost> blogPosts)
    {
        return blogPosts.stream().map((blogPost) -> modelMapper.map(blogPost,BlogPostDto.class)).toList();
    }
    private BlogPost convertToEntity(BlogPostDto blogPostDto)
    {
        return modelMapper.map(blogPostDto, BlogPost.class);
    }
}

























