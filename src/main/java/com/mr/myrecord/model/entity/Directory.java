package com.mr.myrecord.model.entity;

import lombok.*;

import java.util.*;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude =  {"directoryId", "parentDirectoryId", "parent_directory_id"})
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String directoryName;

    @ManyToOne
    private User userDirectoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "directoryId", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentDirectoryId", cascade = CascadeType.ALL)
    private List<Directory> directoryList = new ArrayList<>();

    @JoinColumn(name = "parent_directory_id")
    @ManyToOne
    private Directory parentDirectoryId;

}
