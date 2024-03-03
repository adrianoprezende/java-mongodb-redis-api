package com.rezende.javamongodbredisapi.endpoint.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta implements Serializable {

    @Serial
    private static final long serialVersionUID = 834874568317434600L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalRecords;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPages;
}
