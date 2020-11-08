package com.yu.voiceworks.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class WorkDir {
    @Id
    private String workId;

    private String dirPath;

}
