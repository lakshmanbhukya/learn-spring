package org.example.learnspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryRequest {

    @NotBlank(message = "Country code cannot be blank")
    @Size(min = 2, max = 2, message = "Country code must be exactly 2 characters")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Country code must be 2 uppercase letters (ISO Alpha-2)")
    private String code;

    @NotBlank(message = "Country name cannot be blank")
    @Pattern(regexp = "^[A-Za-z\\s\\-'.]{2,60}$", message = "Country name contains invalid characters")
    private String countryName;

    @NotBlank(message = "Longitude cannot be blank")
    @Pattern(
            regexp = "^[-+]?(180(\\.0{1,7})?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d{1,7})?)$",
            message = "Longitude must be a valid decimal between -180 and 180"
    )
    private String longitude;

    @NotBlank(message = "Latitude cannot be blank")
    @Pattern(
            regexp = "^[-+]?([1-8]?\\d(\\.\\d{1,7})?|90(\\.0{1,7})?)$",
            message = "Latitude must be a valid decimal between -90 and 90"
    )
    private String latitude;
}
