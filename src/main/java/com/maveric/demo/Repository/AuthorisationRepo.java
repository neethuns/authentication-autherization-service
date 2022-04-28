package com.maveric.demo.Repository;

import com.maveric.demo.Model.JWTRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorisationRepo extends JpaRepository<JWTRequest,Integer> {

    JWTRequest findByEmail(String email);
}
