package com.example.javalearnbook.model;

import lombok.Data;


import javax.persistence.*;

import java.util.HashSet;

import java.util.Set;

@Entity
@Table()
@Data
public class Writer {
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
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "writer")
    private Set<PostComment> comments = new HashSet<>();

}
