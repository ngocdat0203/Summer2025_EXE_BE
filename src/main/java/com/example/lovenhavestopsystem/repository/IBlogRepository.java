package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IBlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findByAccountIdAndDeletedTimeNull(int accountId);
    List<Blog> findByDeletedTimeNull();
}
