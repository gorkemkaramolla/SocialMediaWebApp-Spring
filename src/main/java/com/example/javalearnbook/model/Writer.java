package com.example.javalearnbook.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

import java.util.HashSet;

import java.util.Set;

@Entity
@Table()
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @OneToMany(mappedBy = "writer")
    private Set<PostLike> likes = new HashSet<>();






}
