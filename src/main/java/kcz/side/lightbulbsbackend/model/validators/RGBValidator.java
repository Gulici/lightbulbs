package kcz.side.lightbulbsbackend.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kcz.side.lightbulbsbackend.model.BulbColor;

public class RGBValidator implements ConstraintValidator<ValidRGB, BulbColor> {

    @Override
    public boolean isValid(BulbColor bulbColor, ConstraintValidatorContext constraintValidatorContext) {
        return bulbColor.getR() <= 255 && bulbColor.getG() <= 255 && bulbColor.getB() <= 255
                && bulbColor.getR() >= 0 && bulbColor.getG() >= 0 && bulbColor.getB() >= 0;
    }
}
