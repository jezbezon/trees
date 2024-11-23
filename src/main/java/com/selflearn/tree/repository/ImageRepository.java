package com.selflearn.tree.repository;

import com.selflearn.tree.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
