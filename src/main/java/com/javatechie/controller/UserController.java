package com.javatechie.controller;

import com.javatechie.dto.AuthRequest;
import com.javatechie.entity.Product;
import com.javatechie.entity.UserInfo;
import com.javatechie.service.JwtService;
import com.javatechie.service.ProductService;
import com.javatechie.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/new")
    public String addNewUser(@RequestBody @Valid UserInfo userInfo) {
        System.out.println("add user");
        return userService.addUser(userInfo);
    }

    @GetMapping("/getByName/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + " || hasRole('ROLE_USER')")
    public ResponseEntity<Object> getProductByEmail(@PathVariable String email) {
        UserInfo user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);

    }

    @GetMapping("/allUser")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public List<UserInfo> getAllUser() {
        return userService.findAllUser();
    }

    @PostMapping("/authentication")
    public String authRequest(@RequestBody AuthRequest authRequest){
//        try{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            return "username not found";

//        }
//       }catch (Exception e){
//           System.out.println("exception");
//           throw new UsernameNotFoundException("user not found");
//       }
        }
    }
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<Object> deleteById(@PathVariable int id){
//        UserInfo userInfo = userService.deleteById(id);
//        return ResponseEntity.ok(userInfo);
//
//    }
}
