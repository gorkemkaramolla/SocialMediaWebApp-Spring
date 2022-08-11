package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.BlogWriterDto;

import com.example.javalearnbook.model.BlogWriter;
import com.example.javalearnbook.service.BlogWriterService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/writers")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class BlogWriterController {

    private BlogWriterService blogWriterService;

    @GetMapping

    public List<BlogWriterDto> findAllWriters()
    {
        List<BlogWriter> writersList = blogWriterService.findAllWriters();
        return convertListToDto(writersList);

    }
    @GetMapping("/{userId}")
    public BlogWriterDto getOneWriter(@PathVariable Long userId)
    {
        BlogWriter singleWriter= blogWriterService.getOneWriter(userId);
        return convertToDto(singleWriter);
    }
    @PostMapping
    public BlogWriter saveWriter(@RequestBody BlogWriterDto blogWriterDto)
    {
        return blogWriterService.saveWriter(convertToEntity(blogWriterDto));
    }
    @PutMapping("/{userId}")
    public BlogWriterDto updateUser(@PathVariable Long userId,@RequestBody BlogWriterDto blogWriterDto)
    {
        BlogWriter updatingWriter = convertToEntity(blogWriterDto);
        BlogWriter updatedWriter= blogWriterService.updateWriter(userId,updatingWriter);

        return convertToDto(updatedWriter);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    private final ModelMapper modelMapper =  modelMapper();
    private BlogWriterDto convertToDto(BlogWriter writer) {

        return modelMapper.map(writer, BlogWriterDto.class);
    }

    private BlogWriter convertToEntity(BlogWriterDto writerDto){


        return modelMapper.map(writerDto, BlogWriter.class);
    }

    private  List<BlogWriterDto> convertListToDto(List<BlogWriter> writerList)
    {
        return writerList.stream().map((writer) -> modelMapper.map(writer,BlogWriterDto.class)).toList();
    }





























}
