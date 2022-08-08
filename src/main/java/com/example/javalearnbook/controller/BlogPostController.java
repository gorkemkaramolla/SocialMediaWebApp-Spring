package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.BlogPostDto;
import com.example.javalearnbook.model.BlogPost;
import com.example.javalearnbook.repository.BlogPostRepository;
import com.example.javalearnbook.service.BlogPostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class BlogPostController {



    private BlogPostService blogPostService;

    @GetMapping
    @ResponseBody
    public List<BlogPostDto> getPosts()
    {

        List<BlogPost> posts =  blogPostService.getPosts();

        return posts.stream() .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/{postId}")
    public BlogPostDto getSinglePost(@PathVariable Long postId)
    {
        BlogPost post = blogPostService.getSinglePost(postId);

        return convertToDto(post);

    }

    @PostMapping
    public BlogPostDto savePost(@RequestBody BlogPostDto blogPostDto) throws ParseException {
        BlogPost post = convertToEntity(blogPostDto);
        BlogPost postCreated = blogPostService.savePost(post);
        return convertToDto(postCreated);

    }
    @PutMapping("/{userId}")
    public String updateUser(@PathVariable Long userId, @RequestBody BlogPostDto blogPostDto)
    {

        BlogPost willChangePost = convertToEntity(blogPostDto);
        return blogPostService.updatePost(userId,willChangePost)? "User Updated":"User couldn't updated";


    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId)
    {
        return blogPostService.deleteUser(userId)?"deleted":"cant delete";
    }



    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    private final ModelMapper modelMapper =  modelMapper();
    private BlogPostDto convertToDto(BlogPost post) {

        return modelMapper.map(post, BlogPostDto.class);
    }


    private BlogPost convertToEntity(BlogPostDto postDto){


        return modelMapper.map(postDto, BlogPost.class);
    }









}
