package com.example.javalearnbook.model;

import lombok.Data;


import javax.persistence.*;

import java.util.HashSet;

import java.util.Set;

@Entity
@Table()
@Data
public class BlogWriter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String bio;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "writer")
    private Set<BlogPost> posts = new HashSet<>();


}
