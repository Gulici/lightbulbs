package kcz.side.lightbulbsbackend.model.validators;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = RGBValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRGB {
    String message() default "Colors RGB must be between 0 and 255";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
