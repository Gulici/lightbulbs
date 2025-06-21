package kcz.side.lightbulbsbackend.model.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DimValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDim {
    String message() default "Colors dimming must be between 0 and 100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
