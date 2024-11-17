package com.bits_wilp.bookexchangeplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "books")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String title;
    @Column(length = 50)
    private String author;
    private String genre;
    @Column(length = 1000)
    private String description;
    private Boolean availability; // true if available, false if not
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users owner; // Relationship to User who listed the book
    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Exchange> exchanges;

}

