package com.maveric.demo.Feign;


import com.maveric.demo.Model.User;
import com.maveric.demo.Model.UserDto;
import com.maveric.demo.Model.UserWithOutPassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "user-service")
public interface FeignUser {

    @GetMapping("/api/v1/users/getUserByEmail/{emailId}")
    UserDto getUserDetailsByEmail(@PathVariable("emailId") String emailId);

    @PostMapping("/api/v1/users")
    public ResponseEntity<UserDto> createUser(@RequestBody User user);
}
