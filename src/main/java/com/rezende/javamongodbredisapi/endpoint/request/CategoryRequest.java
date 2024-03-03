package com.rezende.javamongodbredisapi.endpoint.request;

import com.rezende.javamongodbredisapi.domain.validation.groups.CreateValidationGroup;
import com.rezende.javamongodbredisapi.domain.validation.groups.UpdateValidationGroup;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import static io.micrometer.common.util.StringUtils.isNotBlank;

public record CategoryRequest(
        @NotBlank(groups = CreateValidationGroup.class) String title,
        @NotBlank(groups = CreateValidationGroup.class) String description,
        @NotBlank(groups = CreateValidationGroup.class) String ownerId) {

    @AssertTrue(groups = UpdateValidationGroup.class, message = "Pelo menos um campo deve estar presente.")
    private boolean hasAtLeastOneNotBlank() {
        return isNotBlank(title()) || isNotBlank(description()) || isNotBlank(ownerId());
    }
}
