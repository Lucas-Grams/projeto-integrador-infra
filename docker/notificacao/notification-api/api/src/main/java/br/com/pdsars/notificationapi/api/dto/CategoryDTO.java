package br.com.pdsars.notificationapi.api.dto;

import br.com.pdsars.notificationapi.api.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {
    @NotBlank(message = "`id` of the channel can not be blank")
    private String id;
    @NotBlank(message = "`name` of the channel can not be blank")
    private String name;

    /**
     * Empty constructor for Jackson
     */
    public CategoryDTO() { }

    public CategoryDTO(final Category category) {
        this.id = category.getExternalId();
        this.name = category.getName();
    }

    public Category toCategory() {
        final var category = new Category();
        category.setExternalId(this.id);
        category.setName(this.name);
        return category;
    }
}
