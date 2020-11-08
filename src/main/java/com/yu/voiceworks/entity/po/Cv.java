package com.yu.voiceworks.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@AllArgsConstructor
@Table
@Entity
public class Cv {
    @Id
    private String cvId;

    private String cvName;

    public Cv() {

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cv cv = (Cv) o;
        return Objects.equals(cvId, cv.cvId) &&
                Objects.equals(cvName, cv.cvName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cvId, cvName);
    }
}
