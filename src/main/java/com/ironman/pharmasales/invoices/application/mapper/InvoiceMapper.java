package com.ironman.pharmasales.invoices.application.mapper;

import com.ironman.pharmasales.clients.application.mapper.ClientMapper;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceFilterDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceSaveDto;
import com.ironman.pharmasales.invoices.application.dto.invoicedetail.InvoiceDetailDto;
import com.ironman.pharmasales.invoices.application.dto.invoicedetail.InvoiceDetailSaveDto;
import com.ironman.pharmasales.invoices.domain.model.InvoiceDetailDomain;
import com.ironman.pharmasales.invoices.domain.model.InvoiceDomain;
import com.ironman.pharmasales.invoices.domain.model.InvoiceFilterDomain;
import com.ironman.pharmasales.products.application.mapper.ProductMapper;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import com.ironman.pharmasales.users.application.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class, ClientMapper.class, UserMapper.class, ProductMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface InvoiceMapper {

    InvoiceDto toDto(InvoiceDomain invoice);

    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "user.id", source = "userId")
    InvoiceDomain toDomain(InvoiceSaveDto dto);

    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "user.id", source = "userId")
    void updateDomain(@MappingTarget InvoiceDomain domain, InvoiceSaveDto dto);

    @Mapping(target = "invoiceDateFrom", expression = "java(new DateHelper().localDateToString(filter.getInvoiceDateFrom()))")
    @Mapping(target = "invoiceDateTo", expression = "java(new DateHelper().localDateToString(filter.getInvoiceDateTo()))")
    InvoiceFilterDomain toFilter(InvoiceFilterDto filter);

    InvoiceDetailDto toDetailDto(InvoiceDetailDomain detail);

    @Mapping(target = "product.id", source = "productId")
    InvoiceDetailDomain toDetailDomain(InvoiceDetailSaveDto detailDto);

    @Mapping(target = "product.id", source = "productId")
    void updateDetailDomain(@MappingTarget InvoiceDetailDomain domain, InvoiceDetailSaveDto detailDto);

}
