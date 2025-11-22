package org.csu.petstore.user.controller;

import org.csu.petstore.user.entity.Journal;
import org.csu.petstore.user.entity.ResetPassword;
import org.csu.petstore.user.entity.Signon;
import org.csu.petstore.user.security.JwtUtil;
import org.csu.petstore.user.service.TokenBlackService;
import org.csu.petstore.user.service.UserService;
import org.csu.petstore.user.vo.AccountVO;
import org.csu.petstore.user.vo.ResetPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenBlackService tokenBlackService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Signon signon) {
        if (userService.login(signon.getUsername(), signon.getPassword())) {
            String token = jwtUtil.generateToken(signon.getUsername());

            Date date = new Date();
            String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
            String loginInString = "User " + signon.getUsername() + " logged in.";
            userService.updateJournal(signon.getUsername(), loginInString, currentDate, "#4472C4");

            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username or password");
    }

    @DeleteMapping("/auth/tokens/current")
    public ResponseEntity<?> signOff(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if (signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username");
        }

        Date date = new Date();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        String loginOutString = "User " + username + " logged out.";
        userService.updateJournal(username, loginOutString, currentDate, "#4472C4");

        if (!tokenBlackService.addToBlackList(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("something went wrong");
        return ResponseEntity.status(HttpStatus.OK).body(loginOutString);
    }

    @PostMapping("/account")
    public ResponseEntity<?> register(@RequestBody AccountVO user) {
        Signon signon = userService.getSignonByUsername(user.getUsername());
        if (signon != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("account already exists");
        }

        Date date = new Date();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        String registerString = "User " + user.getUsername() + " completed registration.";
        user.setStatus("OK");

        userService.updateJournal(user.getUsername(), registerString, currentDate, "#C00000");
        userService.insertAccount(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/account/me")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if (signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user do not exist");
        }
        AccountVO accountVO = userService.getAccountVOByUsername(username);
        accountVO.setPassword(null);
        return ResponseEntity.ok(accountVO);
    }

    @GetMapping("/account/me/myJournal")
    public ResponseEntity<?> getMyJournal(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if (signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user do not exist");
        }
        List<Journal> journals = userService.getAllJournals(username);
        return ResponseEntity.ok(journals);
    }

    @PostMapping("/auth/resetPsw")
    public ResponseEntity<?> resetPassword(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody ResetPasswordDTO resetPasswordDTO) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if (signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user do not exist");
        }
        String password = signon.getPassword();
        if (!password.equals(resetPasswordDTO.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid old password");
        }

        AccountVO accountVO = userService.getAccountVOByUsername(username);
        accountVO.setPassword(resetPasswordDTO.getNewPassword());
        userService.updateAccount(accountVO);
        String newToken = jwtUtil.generateToken(username);
        return ResponseEntity.ok(newToken);
    }

    @PutMapping("/account/me/info")
    public ResponseEntity<?> updateUserInfo(@RequestHeader("Authorization") String authHeader,
                                            @RequestBody AccountVO user) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if (signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user don't exist");
        }
        user.setUsername(signon.getUsername());
        user.setPassword(signon.getPassword());
        userService.updateAccount(user);
        return ResponseEntity.ok(user);
    }
}
