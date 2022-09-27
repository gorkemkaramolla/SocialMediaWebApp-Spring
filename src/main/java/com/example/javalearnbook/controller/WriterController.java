package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.WriterDto;

import com.example.javalearnbook.dto.requests.ProfileImageRequest;
import com.example.javalearnbook.dto.requests.WriterRequest;
import com.example.javalearnbook.dto.responses.WriterResponse;
import com.example.javalearnbook.model.UserNameRequest;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.service.WriterService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/writers")
@CrossOrigin(origins = "http://localhost:3000")

public class WriterController {

    private WriterService writerService;

    @GetMapping

    public List<WriterDto> findAllWriters()
    {
        List<Writer> writersList = writerService.findAllWriters();
        return convertListToDto(writersList);

    }
    @GetMapping("/{userId}")
    public WriterResponse getOneWriter(@PathVariable Long userId)
    {
        Writer singleWriter= writerService.getWriterById(userId);
        WriterResponse writerResponse = new WriterResponse();
        writerResponse.setEmail(singleWriter.getEmail());
        writerResponse.setUserName(singleWriter.getUserName());
        writerResponse.setBio(singleWriter.getBio());
        writerResponse.setImgPath(singleWriter.getImgPath());
        return writerResponse;
    }
    @PostMapping
    public Writer saveWriter(@RequestBody WriterDto writerDto)
    {
        return writerService.saveWriter(convertToEntity(writerDto));
    }
    @PutMapping("/username/{writerId}")
    public String pickUserName(@PathVariable String writerId,@RequestBody UserNameRequest writerRequest)
    {
        return writerService.updateUserName(Long.parseLong(writerId),writerRequest);
    }

    @PutMapping("/{writerId}")
    public WriterResponse updateUser(@PathVariable String writerId,@RequestBody ProfileImageRequest profileImageRequest)
    {
        System.out.println(writerId);
        return writerService.updateWriter(Long.parseLong(writerId),profileImageRequest);
    }
    @DeleteMapping("{writerId}")
    public String deleteWriter(@PathVariable Long writerId)
    {
        return writerService.deleteWriter(writerId);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    private final ModelMapper modelMapper =  modelMapper();
    private WriterDto convertToDto(Writer writer) {

        return modelMapper.map(writer, WriterDto.class);
    }

    private Writer convertToEntity(WriterDto writerDto){


        return modelMapper.map(writerDto, Writer.class);
    }

    private  List<WriterDto> convertListToDto(List<Writer> writerList)
    {
        return writerList.stream().map((writer) -> modelMapper.map(writer,WriterDto.class)).toList();
    }

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }
}
