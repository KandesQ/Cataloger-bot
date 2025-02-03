package com.example.TreeViewBot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "elements", schema = "public")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Element parentElement;

    @OneToMany(mappedBy = "parentElement", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Element> children = new HashSet<>();

    public Element(String name) {
        this.name = name;
    }
}
