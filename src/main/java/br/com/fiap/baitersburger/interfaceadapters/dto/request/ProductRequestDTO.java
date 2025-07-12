package br.com.fiap.baitersburger.interfaceadapters.dto.request;

import br.com.fiap.baitersburger.domain.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequestDTO {
    @NotBlank
    private String productName;

    @NotNull
    private Category category;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotEmpty
    private List<String> imagesUrls;
}
