package com.backend.quack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="link")
public class LinkModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Link name is required")
    @Size(min = 3, max = 20, message = "Link name must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Link url is required")
    @URL(message = "Link url must be a valid URL")
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date updatedAt;
}
