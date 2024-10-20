package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.UserRole;
import com.refanzzzz.tokonyadia.dto.request.MerchantRequest;
import com.refanzzzz.tokonyadia.dto.request.SearchingPagingAndSortingRequest;
import com.refanzzzz.tokonyadia.dto.response.MerchantResponse;
import com.refanzzzz.tokonyadia.entity.Merchant;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import com.refanzzzz.tokonyadia.repository.MerchantRepository;
import com.refanzzzz.tokonyadia.service.MerchantService;
import com.refanzzzz.tokonyadia.service.UserAccountService;
import com.refanzzzz.tokonyadia.specification.MerchantSpecification;
import com.refanzzzz.tokonyadia.util.MapperUtil;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final UserAccountService userAccountService;

    @Override
    public Page<MerchantResponse> getAll(MerchantRequest request) {
        SearchingPagingAndSortingRequest searchRequest = request.getSearchRequest();
        Sort sortBy = SortUtil.parseSort(searchRequest.getSortBy());

        Specification<Merchant> merchantSpecification = MerchantSpecification.getMerchantSpecification(request);
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sortBy);

        return merchantRepository.findAll(merchantSpecification, pageable).map((MapperUtil::toMerchantResponse));
    }

    @Override
    public MerchantResponse getById(String id) {
        Merchant merchant = getOne(id);
        return MapperUtil.toMerchantResponse(merchant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MerchantResponse create(MerchantRequest request) {
        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(UserRole.ROLE_MERCHANT)
                .build();

        userAccountService.create(userAccount);

        Merchant merchant = Merchant.builder()
                .name(request.getName())
                .userAccount(userAccount)
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .email(request.getEmail())
                .build();

        Merchant savedMerchant = merchantRepository.saveAndFlush(merchant);
        return MapperUtil.toMerchantResponse(savedMerchant);
    }

    @Override
    public Merchant getOne(String id) {
        return merchantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));
    }

    @Override
    public void remove(String id) {
        Merchant merchant = getOne(id);
        merchantRepository.delete(merchant);
    }

    @Override
    public MerchantResponse update(String id, MerchantRequest request) {
        Merchant merchant = getOne(id);

        merchant.setName(request.getName());
        merchant.setEmail(request.getEmail());
        merchant.setAddress(request.getAddress());
        merchant.setPhoneNumber(request.getPhoneNumber());

        merchantRepository.saveAndFlush(merchant);

        return MapperUtil.toMerchantResponse(merchant);
    }
}
