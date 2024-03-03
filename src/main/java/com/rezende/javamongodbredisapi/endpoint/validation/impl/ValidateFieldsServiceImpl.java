package com.rezende.javamongodbredisapi.endpoint.validation.impl;


import com.rezende.javamongodbredisapi.domain.error.ErrorEnum;
import com.rezende.javamongodbredisapi.endpoint.response.ErrorData;
import com.rezende.javamongodbredisapi.domain.exception.ValidationException;
import com.rezende.javamongodbredisapi.domain.validation.groups.ValidationGroup;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.text.MessageFormat;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.rezende.javamongodbredisapi.endpoint.validation.ValidateFieldsService;

@RequiredArgsConstructor
@Slf4j
@Component
public class ValidateFieldsServiceImpl<T> implements ValidateFieldsService<T> {

    private static final String PRE_FIX_DETAIL_MESSAGE = "O campo {0} {1}";

    private final ValidatorFactory validatorFactory;

    @Override
    public void validateRequest(final T t, final Class<? extends ValidationGroup> validationGroup) {
        Set<ErrorData> errors = buildErrors(t, this::buildValidationErrorData, validationGroup);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private Set<ErrorData> buildErrors(final T t, final Function<ConstraintViolation<T>, ErrorData> builderAction, final Class<? extends ValidationGroup> validationGroup) {
        return validateEntityFields(t, validationGroup).stream()
                .map(builderAction)
                .collect(Collectors.toSet());
    }

    private Set<ConstraintViolation<T>> validateEntityFields(final T t, Class<? extends ValidationGroup> validationGroup) {
        final Validator validatorJavax = validatorFactory.getValidator();
        return validatorJavax.validate(t, validationGroup);
    }

    private ErrorData buildValidationErrorData(final ConstraintViolation<T> validation) {
        return new ErrorData(
                ErrorEnum.VALIDATION_ERROR.getDescription(),
                ErrorEnum.VALIDATION_ERROR.getCode(),
                getPersonalizedMessage(validation));
    }

    private String getPersonalizedMessage(final ConstraintViolation<T> validation) {
        if (validation.getMessageTemplate().startsWith("{")) {
            return MessageFormat.format(PRE_FIX_DETAIL_MESSAGE, validation.getPropertyPath(), validation.getMessage());
        }
        return validation.getMessage();
    }
}
