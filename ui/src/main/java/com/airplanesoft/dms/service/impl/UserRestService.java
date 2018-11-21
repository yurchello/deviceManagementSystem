package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.airplanesoft.dms.utils.URLConstants.*;

@Service
public class UserRestService implements UserService {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDto getById(Integer id) {
        logger.info("Get user id=" + id);
        return restTemplate.exchange(USERS + DELIM + id, HttpMethod.GET, getJsonEntity(), UserDto.class).getBody();
    }

    @Override
    public UserDto getByEmail(String email) {
        return null;
    }

    @Override
    public long count() {
        logger.info("Get users count.");
        return restTemplate.exchange(USERS + COUNT, HttpMethod.GET, getJsonEntity(), Long.class).getBody();
    }

    @Override
    public long count(UserDto criteria) {
        return 0;
    }

    @Override
    public List<UserDto> findAll(Pageable pageable) {
        logger.info("Find users: " + " pageSize=" + pageable.getPageSize() + " pageNumber=" + pageable.getPageNumber());
        String uriString = buildUriString(USERS, pageable);
        return makeGetRequest(uriString, new ParameterizedTypeReference<List<UserDto>>() {}).getBody();
    }

    @Override
    public List<UserDto> findAll(UserDto criteria, Pageable pageable) {
        return null;
    }

    @Override
    public Set<DeviceDto> getDevicesByUserId(Integer userId, Pageable pageable) {
        logger.info("Find devices assigned to user userId=" + userId + " pageSize=" + pageable.getPageSize() + " pageNumber=" + pageable.getPageNumber());
        String uriString = buildUriString( USERS + DELIM + userId + DEVICES, pageable);
        return makeGetRequest(uriString, new ParameterizedTypeReference<Set<DeviceDto>>() {}).getBody();
    }

    public void saveDevice(UserDto userDto, DeviceDto deviceDto){
        logger.info("Save device assigned to user user: " + userDto + " device: " + deviceDto );
        restTemplate.put( USERS + DELIM + userDto.getId() +  DEVICES + DELIM, deviceDto, Void.class);
    }

    @Override
    public long countDevices(Integer userId) {
        logger.info("Count devices by  user id=" + userId);
        return restTemplate.exchange(USERS + DELIM + userId + DEVICES + COUNT, HttpMethod.GET, getJsonEntity(), Long.class).getBody();
    }


    private String buildUriString(String path, UserDto criteria) {
        return UriComponentsBuilder.fromPath(path)
                .queryParam("userName", criteria.getFirstName())
                .queryParam("lastName", criteria.getLastName())
                .queryParam("email", criteria.getEmail())
                .queryParam("jobPositions", criteria.getJobPositions())
                .build()
                .toUriString();
    }

    private String buildUriString(String path, Pageable pageable) {
        Map<String, List<String>> sort = Optional.ofNullable(pageable.getSort())
                .map(s -> StreamSupport.stream(s.spliterator(), false))
                .orElse(Stream.empty())
                .map(order -> Pair.of("sort", order.getProperty() + "," + order.getDirection()))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));

        return UriComponentsBuilder.fromPath(path)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParams(CollectionUtils.toMultiValueMap(sort))
                .build()
                .toUriString();
    }

    private String buildUriString(String path, UserDto criteria, Pageable pageable) {
        Map<String, List<String>> sort = Optional.ofNullable(pageable.getSort())
                .map(s -> StreamSupport.stream(s.spliterator(), false))
                .orElse(Stream.empty())
                .map(order -> Pair.of("sort", order.getProperty() + "," + order.getDirection()))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));

        return UriComponentsBuilder.fromPath(path)
                .queryParam("userName", criteria.getFirstName())
                .queryParam("lastName", criteria.getLastName())
                .queryParam("email", criteria.getEmail())
                .queryParam("jobPositions", criteria.getJobPositions())
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParams(CollectionUtils.toMultiValueMap(sort))
                .build()
                .toUriString();
    }

    private <T> ResponseEntity<T> makeGetRequest(String uriString, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(uriString, HttpMethod.GET, getJsonEntity(), responseType);
    }
}
