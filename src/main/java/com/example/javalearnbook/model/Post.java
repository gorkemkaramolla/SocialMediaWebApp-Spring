package com.example.javalearnbook.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table()
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "writer_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Writer writer;


    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private Set<PostComment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    private Set<Like> likes = new HashSet<>();



}
