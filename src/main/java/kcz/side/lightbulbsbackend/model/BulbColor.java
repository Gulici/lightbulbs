package kcz.side.lightbulbsbackend.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BulbColor {

    public interface RGBGroup {}
    public interface DimGroup {}

    @Min(value = 0, groups = RGBGroup.class)
    @Max(value = 255, groups = RGBGroup.class)
    private int r,g,b;
    @Min(value = 0, groups = DimGroup.class)
    @Max(value = 100, groups = DimGroup.class)
    private int d;

    private int temp;
}
