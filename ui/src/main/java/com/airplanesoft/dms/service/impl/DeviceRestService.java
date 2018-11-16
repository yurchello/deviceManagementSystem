package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.service.DeviceService;
import com.airplanesoft.dms.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static com.airplanesoft.dms.utils.URLConstants.*;

public class DeviceRestService implements DeviceService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DeviceDto getById(Integer id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long count(DeviceDto criteria) {
        return 0;
    }

    @Override
    public List<DeviceDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<DeviceDto> findAll(DeviceDto criteria, Pageable pageable) {
        return null;
    }

    @Override
    public Set<DeviceDto> getDevicesByUserId(Integer userId) {
        return null;
    }

    @Override
    public void save(DeviceDto deviceDto) {
        restTemplate.put( DEVICES + "/", deviceDto, Void.class);
    }
}
