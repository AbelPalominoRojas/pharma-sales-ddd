package com.ironman.pharmasales.invoices.infrastructure.persistence.mapper;

import com.ironman.pharmasales.clients.infrastructure.persistence.mapper.ClientEntityMapper;
import com.ironman.pharmasales.invoices.domain.model.InvoiceDetailDomain;
import com.ironman.pharmasales.invoices.domain.model.InvoiceDomain;
import com.ironman.pharmasales.invoices.infrastructure.persistence.entity.Invoice;
import com.ironman.pharmasales.invoices.infrastructure.persistence.entity.InvoiceDetail;
import com.ironman.pharmasales.products.infrastructure.persistence.mapper.ProductEntityMapper;
import com.ironman.pharmasales.users.infrastructure.persistence.mapper.UserEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientEntityMapper.class, UserEntityMapper.class, ProductEntityMapper.class}
)
public interface InvoiceEntityMapper {
    InvoiceDomain toDomain(Invoice entity);


    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "userId", source = "user.id")
    Invoice toEntity(InvoiceDomain domain);

    @Mapping(target = "invoice.id", source = "id.invoiceId")
    InvoiceDetailDomain toDomainDetail(InvoiceDetail entity);


    @Mapping(target = "id.invoiceId", source = "invoice.id")
    @Mapping(target = "id.productId", source = "product.id")
    @Mapping(target = "invoice", ignore = true)
    InvoiceDetail toEntityDetail(InvoiceDetailDomain domain);
}
