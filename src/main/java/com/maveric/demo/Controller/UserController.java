package com.maveric.demo.Controller;

import com.maveric.demo.Const.ConstFile;
import com.maveric.demo.Feign.FeignUser;
import com.maveric.demo.Model.*;
import com.maveric.demo.Repository.AuthorisationRepo;
import com.maveric.demo.Service.UserService;
import com.maveric.demo.Utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(value="*")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
   private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;

    @Autowired
    FeignUser feignUser;

    @Autowired
    AuthorisationRepo authorisationRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @CrossOrigin(value = "*")
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                     loginRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            System.out.println("I am here   ");
            throw new Exception(ConstFile.errorCode, ex);
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtUtility.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new JWTResponse(token, feignUser.getUserDetailsByEmail(loginRequest.getEmail())));
    }

    @CrossOrigin(value = "*")
    @PostMapping("/signup")
    public ResponseEntity<JWTResponse> signup(@RequestBody com.maveric.demo.Model.User user) throws Exception {


        JWTRequest jwtRequest = new JWTRequest();
        jwtRequest.setEmail(user.getEmail());
        String password=bCryptPasswordEncoder.encode(user.getPassword());
        jwtRequest.setPassword(password);

        authorisationRepo.save(jwtRequest);
        user.setPassword(password);
        UserDto userDto = feignUser.createUser(user).getBody();
        final UserDetails userDetails = new User(user.getEmail(),password, new ArrayList<>());
        final String token = jwtUtility.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new JWTResponse(token,userDto));
    }
}
