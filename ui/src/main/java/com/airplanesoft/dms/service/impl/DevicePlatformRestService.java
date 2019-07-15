package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.dto.DevicePlatformDTO;
import com.airplanesoft.dms.http.RestResponse;
import com.airplanesoft.dms.service.DevicePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static com.airplanesoft.dms.utils.URLConstants.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
public class DevicePlatformRestService implements DevicePlatformService {

    private final Log logger = LogFactory.getLog(getClass());


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<DevicePlatformDTO> getAll() {
        logger.info("Get all device platforms.");
        return restTemplate.exchange(DEVICE_PLATFORM, HttpMethod.GET, getJsonEntity(), new ParameterizedTypeReference<RestResponse<List<DevicePlatformDTO>>>() {}).getBody().getPayload();
    }

    @Override
    public long count() {
        logger.info("Count all device platforms.");
        return restTemplate.exchange(DEVICE_PLATFORM + COUNT, HttpMethod.GET, getJsonEntity(), new ParameterizedTypeReference<RestResponse<Long>>(){}).getBody().getPayload();
    }

}
