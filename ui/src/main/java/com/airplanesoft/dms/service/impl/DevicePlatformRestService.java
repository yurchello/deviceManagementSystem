package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.dto.DevicePlatformDTO;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.DevicePlatformService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.airplanesoft.dms.utils.URLConstants.*;

@Service
public class DevicePlatformRestService implements DevicePlatformService {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<DevicePlatformDTO> getAll() {
        return restTemplate.exchange(DEVICE_PLATFORMS, HttpMethod.GET, getJsonEntity(), new ParameterizedTypeReference<List<DevicePlatformDTO>>() {}).getBody();
    }

    @Override
    public long count() {
        return restTemplate.exchange(DEVICE_PLATFORMS + COUNT, HttpMethod.GET, getJsonEntity(), Long.class).getBody();
    }

}
