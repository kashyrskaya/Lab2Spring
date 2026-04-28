package lt.esdc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MagicalWordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicalWord {
    
    String message() default "Description must contain a magical word (e.g., Elixir, Brew, Potion, Draught)";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}