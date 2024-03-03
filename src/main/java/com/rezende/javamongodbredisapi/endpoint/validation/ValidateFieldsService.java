package com.rezende.javamongodbredisapi.endpoint.validation;

import com.rezende.javamongodbredisapi.domain.validation.groups.ValidationGroup;

public interface ValidateFieldsService<T> {

    void validateRequest(T t, final Class<? extends ValidationGroup> validationGroup);
}
