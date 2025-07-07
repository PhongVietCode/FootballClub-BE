package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.AddressCreateRequest;
import com.example.footballclub.dto.request.AddressUpdateRequest;
import com.example.footballclub.dto.request.MemberCreateRequest;
import com.example.footballclub.dto.request.MemberUpdateRequest;
import com.example.footballclub.dto.response.AddressResponse;
import com.example.footballclub.dto.response.MemberResponse;
import com.example.footballclub.entity.Address;
import com.example.footballclub.entity.Member;
import com.example.footballclub.enums.MemberStatus;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.decorator.MemberDecorator;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressCreateRequest request);

    AddressResponse toAddressResponse(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(AddressUpdateRequest request, @MappingTarget Address address);

}
