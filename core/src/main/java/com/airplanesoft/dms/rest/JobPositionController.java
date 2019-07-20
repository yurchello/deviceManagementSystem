package com.airplanesoft.dms.rest;

import com.airplanesoft.dms.dto.JobPositionDto;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.http.RestResponse;
import com.airplanesoft.dms.service.JobPositionService;
import com.airplanesoft.dms.util.ToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/job-position")
public class JobPositionController {

    @Autowired
    private JobPositionService jobPositionService;

    @GetMapping(path = "/{name}")
    public RestResponse<JobPositionDto> getJobPosition(@PathVariable String name) {
        return new RestResponse<>(ToDTO.fromJobPosition(jobPositionService.getByName(name)));
    }

    @GetMapping(path = "")
    public RestResponse<Set<JobPositionDto>> getJobPositions() {
        return new RestResponse<>(jobPositionService.findAll().stream()
                .map(ToDTO::fromJobPosition)
                .collect(Collectors.toSet()));
    }
}
