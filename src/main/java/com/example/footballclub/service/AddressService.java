package com.example.footballclub.service;


import com.example.footballclub.dto.request.AddressCreateRequest;
import com.example.footballclub.dto.request.AddressUpdateRequest;
import com.example.footballclub.dto.response.AddressResponse;
import com.example.footballclub.entity.Address;
import com.example.footballclub.entity.Organization;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.AddressMapper;
import com.example.footballclub.repository.AddressRepository;
import com.example.footballclub.repository.OrganizationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {
    AddressMapper addressMapper;
    AddressRepository addressRepository;
    OrganizationRepository organizationRepository;
    public AddressResponse createAddress(AddressCreateRequest request) {
        Organization organization = organizationRepository.findById(request.getOrgId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_ORGANIZATION));
        Address address = addressMapper.toAddress(request);
        address.setOrganization(organization);
        address = addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse updateAddress(String id, AddressUpdateRequest request) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_ADDRESS));
        addressMapper.update(request, address);
        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }
}
