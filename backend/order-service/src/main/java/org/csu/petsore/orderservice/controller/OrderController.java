package org.csu.petsore.orderservice.controller;


import org.csu.petsore.orderservice.common.CommonResponse;
import org.csu.petsore.orderservice.entity.UserAddress;
import org.csu.petsore.orderservice.security.JwtUtil;
import org.csu.petsore.orderservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/orders/addresses")
    @ResponseBody
    public CommonResponse<Object> getAddresses(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }else {
            List<UserAddress> userAddressList = userService.getUserOKAddressByUsername(username);
            return CommonResponse.createForSuccess(userAddressList);
        }
    }

    @DeleteMapping("/addresses/{addressId}")
    @ResponseBody
    public CommonResponse<Object> deleteAddress(@PathVariable("addressId") String addressId,
                                                @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }
        userService.deleteUserAddress(username,addressId);
        return CommonResponse.createForSuccess("delete success");
    }

    @PutMapping("/addresses/{addressId}")
    @ResponseBody
    public CommonResponse<Object> updateAddress(@PathVariable("addressId") String addressId,
                                                @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }
        userService.updateMainAddress(username,addressId);
        return CommonResponse.createForSuccess("set main success");

    }

    @GetMapping("/addresses/{addressId}")
    @ResponseBody
    public CommonResponse<Object> getAddress(@PathVariable("addressId") String addressId,
                                             @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }
        UserAddress userAddress = userService.getUserAddressByAddressId(username,addressId);
        return CommonResponse.createForSuccess(userAddress);
    }
}
