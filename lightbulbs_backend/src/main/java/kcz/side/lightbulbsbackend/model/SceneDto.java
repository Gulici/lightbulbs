package kcz.side.lightbulbsbackend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SceneDto {
    @NotBlank
    String id;
}
