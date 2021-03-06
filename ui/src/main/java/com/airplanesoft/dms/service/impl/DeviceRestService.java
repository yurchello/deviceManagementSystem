package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.service.DeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Set;
import static com.airplanesoft.dms.utils.URLConstants.*;

@Service
public class DeviceRestService implements DeviceService {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DeviceDto getById(Integer id) {
        logger.info("Get device id=" + id);
        return restTemplate.exchange(DEVICES + DELIM + id, HttpMethod.GET, getJsonEntity(), DeviceDto.class).getBody();
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
        logger.info("Save device: " + deviceDto);
        restTemplate.put( DEVICES + DELIM, deviceDto, Void.class);
    }

    @Override
    public void updateDeviceState(Integer id, String state) {
        logger.info("Update device state id=: " + id + " state=" + state);
        restTemplate.put( DEVICES + DELIM + id + DEVICE_STATE + DELIM + state, getJsonEntity(), Void.class);
    }
}
