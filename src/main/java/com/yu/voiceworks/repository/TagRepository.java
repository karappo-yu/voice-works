package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,String> {
}
