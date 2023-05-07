package org.atmosfer.seed.businessservice.feignClient;

import org.atmosfer.seed.businessservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
public interface UserClient {
    @RequestMapping("user/username/{username}")
    UserDto getByUsername(@PathVariable String username);
}
