package com.rezende.javamongodbredisapi.domain.category;

import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@Getter
@Setter
@NoArgsConstructor
@ToString
@CompoundIndexes({
        @CompoundIndex(name = "ownerId_idx", def = "{'ownerId' : 1}")
})
public class Category {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
}
