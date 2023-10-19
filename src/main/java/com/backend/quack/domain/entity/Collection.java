package com.backend.quack.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "collections")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String slug;

    private String bio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Boolean deleted = false;

    @Enumerated(EnumType.STRING)
    private CollectionVisibility visibility = CollectionVisibility.PUBLIC;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static String slugify(String name) {
        String slug = name.replaceAll("[^a-zA-Z0-9\\s]", "");
        slug = slug.toLowerCase();
        slug = slug.replaceAll("\\s", "-");
        return slug;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void update(Collection collection) {
        if (collection.getName() != null && !collection.getName().equals(this.name)) {
            this.name = collection.getName();
            this.slug = slugify(collection.getName());

        }

        if (collection.getBio() != null) {
            this.bio = collection.getBio();
        }

        if (collection.getVisibility() != null) {
            this.visibility = collection.getVisibility();
        }
    }

    public void delete() {
        this.links.forEach(Link::delete);
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
