package com.rezende.javamongodbredisapi.domain.product;

import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
@ToString
@CompoundIndexes({
        @CompoundIndex(name = "ownerId_idx", def = "{'ownerId' : 1}"),
        @CompoundIndex(name = "category_idx", def = "{'category' : 1}")
})
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = -4204999018805351166L;

    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    private String category;
}
