package com.yu.voiceworks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Cv {
    private String cvId;

    private String cvName;

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
