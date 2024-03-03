package com.rezende.javamongodbredisapi.usecase.base;


import com.rezende.javamongodbredisapi.endpoint.request.UrlMeta;
import com.rezende.javamongodbredisapi.endpoint.response.Meta;
import com.rezende.javamongodbredisapi.endpoint.response.Response;
import com.rezende.javamongodbredisapi.endpoint.response.ResponseLinks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BasePagedUseCase<T extends Serializable> {

    private final PagedResourcesAssembler<T> pagedResourcesAssembler;

    public BasePagedUseCase(final PagedResourcesAssembler<T> pagedResourcesAssembler) {
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public Response<T> execute(final Page<T> pagedCollection, final UrlMeta urlMeta) {
        return Response.<T>builder()
                .data(pagedCollection.getContent())
                .links(buildResponseLinks(urlMeta, pagedCollection))
                .meta(buildResponseMeta(pagedCollection.getTotalPages(), pagedCollection.getTotalElements()))
                .build();
    }

    private Meta buildResponseMeta(final int totalPages, final long totalRecords) {
        return Meta.builder()
                .totalRecords(totalRecords)
                .totalPages(totalPages)
                .build();
    }

    private ResponseLinks buildResponseLinks(final UrlMeta urlMeta, final Page<T> pagedCollection) {
        final PagedModel<EntityModel<T>> pagedModel = pagedResourcesAssembler.toModel(pagedCollection);
        return ResponseLinks.builder()
                .self(getLink(urlMeta, pagedModel.getLink(IanaLinkRelations.SELF)))
                .first(getLink(urlMeta, pagedModel.getLink(IanaLinkRelations.FIRST)))
                .next(getLink(urlMeta, pagedModel.getNextLink()))
                .prev(getLink(urlMeta, pagedModel.getPreviousLink()))
                .last(getLink(urlMeta, pagedModel.getLink(IanaLinkRelations.LAST)))
                .build();
    }

    private String getLink(final UrlMeta urlMeta, final Optional<Link> link) {
        String returnLink = link.map(Link::getHref).orElse("");
        if (link.isPresent()) {
            final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(link.get().getHref());
            returnLink = builder
                    .scheme(urlMeta.getScheme())
                    .host(urlMeta.getRequestHost())
                    .port(null)
                    .replacePath(urlMeta.getUrl())
                    .build().toUriString();
        }
        return returnLink;
    }

}
