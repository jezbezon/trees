package com.selflearn.tree.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="fileType")
    private String fileType;

    @Column(name = "fileName", length = 128)
    @Size( max = 128 , message = "fileName can't be more than 128 characters")
    private String fileName;

    @Column(name = "download_url", nullable = false)
    private String downloadUrl;

    @Column(name = "images")
    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
