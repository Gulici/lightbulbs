package kcz.side.lightbulbsbackend.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kcz.side.lightbulbsbackend.model.BulbColor;

public class DimValidator implements ConstraintValidator<ValidDim, BulbColor> {
    @Override
    public boolean isValid(BulbColor bulbColor, ConstraintValidatorContext constraintValidatorContext) {
        return bulbColor.getD() >= 0 && bulbColor.getD() <= 100;
    }
}
