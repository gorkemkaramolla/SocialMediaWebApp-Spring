package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.WriterDto;

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
    public WriterDto getOneWriter(@PathVariable Long userId)
    {
        Writer singleWriter= writerService.getWriterById(userId);
        return convertToDto(singleWriter);
    }
    @PostMapping
    public Writer saveWriter(@RequestBody WriterDto writerDto)
    {
        return writerService.saveWriter(convertToEntity(writerDto));
    }
    @PutMapping("/{writerId}")
    public WriterDto updateUser(@PathVariable Long writerId,@RequestBody WriterDto writerDto)
    {
        Writer updatingWriter = convertToEntity(writerDto);
        Writer updatedWriter= writerService.updateWriter(writerId,updatingWriter);

        return convertToDto(updatedWriter);
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
