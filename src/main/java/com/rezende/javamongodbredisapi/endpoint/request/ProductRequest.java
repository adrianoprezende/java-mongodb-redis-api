package com.rezende.javamongodbredisapi.endpoint.request;

import com.rezende.javamongodbredisapi.domain.validation.groups.CreateValidationGroup;
import com.rezende.javamongodbredisapi.domain.validation.groups.UpdateValidationGroup;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static io.micrometer.common.util.StringUtils.isNotBlank;

public record ProductRequest(
        @NotBlank(groups = CreateValidationGroup.class) String title,
        @NotBlank(groups = CreateValidationGroup.class) String description,
        @NotBlank(groups = CreateValidationGroup.class) String ownerId,
        @NotNull(groups = CreateValidationGroup.class) Integer price,
        @NotBlank(groups = CreateValidationGroup.class) String categoryId) {

    @AssertTrue(groups = UpdateValidationGroup.class)
    private boolean hasAtLeastOneNotBlank() {
        return isNotBlank(title()) || isNotBlank(description()) || isNotBlank(ownerId()) || price() != null || isNotBlank(categoryId());
    }
}
