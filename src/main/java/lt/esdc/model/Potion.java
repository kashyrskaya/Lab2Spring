package lt.esdc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.esdc.validation.MagicalWord;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Potion {

    @NotBlank(message = "Potion code is required")
    @Size(min = 3, max = 3, message = "Code must be exactly 3 characters")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Code must consist of 3 uppercase letters")
    private String code;

    @NotBlank(message = "Potion name is required")
    private String name;

    @Min(value = 1, message = "Power level must be greater than 0")
    private int powerLevel;

    @MagicalWord
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String alchemistId;
}