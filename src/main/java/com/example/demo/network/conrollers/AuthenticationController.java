package com.example.demo.network.conrollers;

import com.example.demo.network.models.AuthenticationRequest;
import com.example.demo.network.models.AuthenticationResponse;
import com.example.demo.network.models.UserModel;
import com.example.demo.network.service.UserService;
import com.example.utils.JwaUtils;
//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/authenticate")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwaUtils jwtTokenUtil;

    @PostMapping(path = "signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException exception) {
            throw new IllegalStateException("Incorrect username or password");
        }

        final UserDetails userDetails = userService.loadUserByUsername(username);
        final UserModel user = userService.getUserByName(username);

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        JSONObject responseData = new JSONObject();
        responseData.put("token", new AuthenticationResponse(jwt).getJwt());
        responseData.put("user", user);


        return ResponseEntity.ok(responseData);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<?> subscribeClient(@RequestBody UserModel registeredUser) {
        String username = registeredUser.getUsername();
        String password = registeredUser.getPassword();
        userService.addNewUser(registeredUser);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException exception) {
            throw new IllegalStateException("Incorrect username or password");
        }

        final UserDetails userDetails = userService.loadUserByUsername(username);
        final UserModel user = userService.getUserByName(username);

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        JSONObject responseData = new JSONObject();
        responseData.put("token", new AuthenticationResponse(jwt).getJwt());
        responseData.put("user", user);

        return ResponseEntity.ok(responseData);
    }
}
