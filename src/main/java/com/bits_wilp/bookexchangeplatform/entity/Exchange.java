package com.bits_wilp.bookexchangeplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private Users initiator;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Users recipient;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private Boolean confirmed;
}
