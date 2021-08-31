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
@ToString(exclude =  {"directoryId"})
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String directoryName;

    @ManyToOne
    private User userDirectoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "directoryId", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

}
