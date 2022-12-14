package org.codefirst.seed.businessservice.feignClient;

import org.codefirst.seed.businessservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user")
public interface UserClient {
    @RequestMapping("/username/{username}")
    UserDto getByUsername(@PathVariable String username);
}
