package org.csu.petstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.csu.petstore.entity.*;
import org.csu.petstore.security.JwtUtil;
import org.csu.petstore.service.CatalogService;
import org.csu.petstore.service.OrderService;
import org.csu.petstore.service.TokenBlackService;
import org.csu.petstore.service.UserService;
import org.csu.petstore.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RestController
@Controller
@RequestMapping("/api/v1")
@SessionAttributes(value = {"loginAccount","captcha","cart","isAdd","languages","categories","myList"})
public class UserController {

    private String msg;
    @Autowired
    private UserService userService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TokenBlackService tokenBlackService;
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Signon signon) {
        if(userService.login(signon.getUsername(), signon.getPassword())) {
            String token = jwtUtil.generateToken(signon.getUsername());
            return ResponseEntity.ok(token);
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = formatter.format(date);
        String loginInString = "User "+ signon.getUsername() + " logged in.";
        userService.updateJournal(signon.getUsername(), loginInString, currentDate, "#4472C4");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username or password");
    }

    @DeleteMapping("/auth/tokens/current")
    public ResponseEntity<?> signOff(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if(username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if(signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username");
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = formatter.format(date);
        String loginOutString = "User "+ username + " logged out.";
        userService.updateJournal(username, loginOutString, currentDate, "#4472C4");

        if(!tokenBlackService.addToBlackList(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("something went wrong");
        return ResponseEntity.status(HttpStatus.OK).body(loginOutString);
    }

    @PostMapping("/account")
    public ResponseEntity<?> register(@RequestBody AccountVO user) {
        Signon signon = userService.getSignonByUsername(user.getUsername());
        if(signon != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("account already exists");
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = formatter.format(date);
        String registerString = "User "+ user.getUsername() + " completed registration.";
        user.setStatus("OK");

        userService.updateJournal(user.getUsername(), registerString, currentDate, "#C00000");
        userService.insertAccount(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/login/forget")
    public ResponseEntity<?> forgetPassword(@RequestBody Signon user)
    {
        System.out.println(user.getUsername());
        Signon signon = userService.getSignonByUsername(user.getUsername());
        String resetMessage;
        if(signon == null){
            //用户不存在，返回注册页面并显示错误消息
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User doesn't exist");
        }
        else{
            System.out.println(signon.getUsername());
            ResetPassword check = userService.getResetPasswordByUserId(user.getUsername());
            if(check == null) {
                ResetPassword resetPassword = new ResetPassword();
                resetPassword.setUserId(user.getUsername());
                resetPassword.setStatus(0);
                userService.addResetPassword(resetPassword);
            }
            else{
                check.setStatus(0);
                userService.updateResetPassword(check);
            }

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentDate = formatter.format(date);
            String forgetPswString = "User "+ user.getUsername() + " submit a forget password request.";
            userService.updateJournal(user.getUsername(), forgetPswString, currentDate, "#4472C4");

            //返回注册页面并显示成功消息
            return ResponseEntity.ok("You have submitted a password reset request. Please wait for the administrator's review.");
        }
    }

    @GetMapping("/account/me")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if(signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user do not exist");
        }
        AccountVO accountVO = userService.getAccountVOByUsername(username);
        accountVO.setPassword(null);
        return ResponseEntity.ok(accountVO);
    }

    @GetMapping("/account/me/myOrders")
    public ResponseEntity<?> getMyOrders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if(signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user do not exist");
        }

        List<OrderVO> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/account/myOrders/cancel")
    public ResponseEntity<?> sendReturnRequest(@RequestHeader("Authorization") String authHeader,
                                               @ModelAttribute OrderCancelDTO orderCancelDTO) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if(signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user dose not exist");
        }

        String imagePath = null;
        if (orderCancelDTO.getImage() != null) {
            System.out.println(orderCancelDTO.getImage());
            try {
                String uploadDir = System.getProperty("user.dir") + "/../Images/";
                System.out.println(uploadDir);
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs(); // 创建目录
                }

                String fileName = orderCancelDTO.getOrderId() + ".jpg";
                File destFile = new File(uploadDir, fileName);
                orderCancelDTO.getImage().transferTo(destFile);

                //存储图片路径
                imagePath = "/../Images/" + fileName;
                System.out.println(imagePath);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Exception occured"); // 返回订单页面
            }
        }

        orderService.insertReturnOrder(orderCancelDTO.getOrderId(), orderCancelDTO.getReason(),
                orderCancelDTO.getDescription(), imagePath);

        orderService.updateStatus(orderCancelDTO.getOrderId());

        AccountVO loginAccount = userService.getAccountVOByUsername(username);
        List<OrderVO> orderVOList = orderService.getOrdersByUsername(loginAccount.getUsername());

        return ResponseEntity.ok("The return request has been sent and is waiting to be reviewed by the merchant.");
    }

    @GetMapping("/account/me/myJournal")
    public ResponseEntity<?> getMyJournal(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if(username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if(signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user do not exist");
        }
        List<Journal> journals = userService.getAllJournals(username);
        return ResponseEntity.ok(journals);
    }

    @PostMapping("/auth/resetPsw")
    public ResponseEntity<?> checkResetPassword(@RequestHeader("Authorization") String authHeader,
                                @RequestBody ResetPasswordDTO resetPasswordDTO){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if(username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if(signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user do not exist");
        }
        String password = signon.getPassword();
        if(!password.equals(resetPasswordDTO.getOldPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid old password");
        }

        AccountVO accountVO = userService.getAccountVOByUsername(username);
        accountVO.setPassword(resetPasswordDTO.getNewPassword());
        userService.updateAccount(accountVO);
        String newToken = jwtUtil.generateToken(username);
        return ResponseEntity.ok(newToken);
    }

    @PutMapping("/account/me/info")
    public ResponseEntity<?> updateUserInfo(@RequestHeader("Authorization") String authHeader,@RequestBody AccountVO user){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if(username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
        }
        Signon signon = userService.getSignonByUsername(username);
        if(signon == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user don't exist");
        }
        user.setUsername(signon.getUsername());
        user.setPassword(signon.getPassword());
        userService.updateAccount(user);
        return ResponseEntity.ok(user);
    }

}
