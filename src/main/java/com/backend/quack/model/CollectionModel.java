package com.backend.quack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="collection")
public class CollectionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Collection name is required")
    @Size(min = 3, max = 20, message = "Collection name must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Collection slug is required")
    @Column(unique = true)
    private String slug;

    @Null
    @Size(max = 100, message = "Collection bio must be less than 100 characters")
    private String bio;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CollectionVisibility visibility = CollectionVisibility.PUBLIC;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "collection_id")
    private List<LinkModel> linkModels;

    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date updatedAt;
}
