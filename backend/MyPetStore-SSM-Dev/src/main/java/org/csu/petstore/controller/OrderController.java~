package org.csu.petstore.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.csu.petstore.common.CommonResponse;
import org.csu.petstore.entity.OrderStatus;
import org.csu.petstore.entity.UserAddress;
import org.csu.petstore.security.JwtUtil;
import org.csu.petstore.service.OrderService;
import org.csu.petstore.service.UserService;
import org.csu.petstore.vo.AccountVO;
import org.csu.petstore.vo.CartVO;
import org.csu.petstore.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes(value = {"productList","order","shippingAddressRequired","loginAccount","addresses","confirmed"})
public class OrderController {
    private boolean shippingAddressRequiredBool;
    private boolean shipAddressSubmittedBool;
    private boolean confirmedBool;
    private boolean newOrderFormSubmited;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/orders")
    @ResponseBody
    public CommonResponse<Object> orders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }else {
            AccountVO loginAccount = userService.getAccountVOByUsername(username);
            List<OrderVO> orderVOList = orderService.getOrdersByUsername(loginAccount.getUsername());
            return CommonResponse.createForSuccess(orderVOList);
        }
    }

    @GetMapping("/orders/{orderId}")
    @ResponseBody
    public CommonResponse<Object> viewOrder(@PathVariable int orderId,
                                            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }
        OrderVO orderVO = orderService.getOrderWithLineItem(orderId);
        return CommonResponse.createForSuccess(orderVO);
    }

    @GetMapping("/carts/orders")
    @ResponseBody
    public CommonResponse<Object> viewNewOrder(@RequestHeader("Authorization") String authHeader,HttpSession session) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }else {
            AccountVO loginAccount = userService.getAccountVOByUsername(username);
            CartVO cart = userService.getCart(loginAccount.getUsername());
            OrderVO orderVO = new OrderVO();
            String isModifying = orderService.checkModifying(cart);
            if (isModifying != null) {
                return CommonResponse.createForError(2,isModifying + " information is being modified, order cannot be placed！");
            }
            String isQuantity = orderService.checkItemQuantity(cart);
            if (isQuantity != null) {
                return CommonResponse.createForError(3,isQuantity + " not enough stock to place an order！");
            }
            orderVO.initOrder(loginAccount, cart);
            session.setAttribute("order", orderVO);
            return CommonResponse.createForSuccess(orderVO);
        }
    }


/*    @PutMapping("/orders/addresses")
    @ResponseBody
    public CommonResponse<Object> getAddresses(@RequestHeader("Authorization") String authHeader,
                                               HttpSession session,
                                               @RequestParam(required = false,value = "order.shippingAddress1") String shippingAddress1,
                                               @RequestParam(required = false,value = "order.shippingAddress2") String shippingAddress2,
                                               @RequestParam(required = false,value = "order.shippingCity") String shippingCity,
                                               @RequestParam(required = false,value = "order.shippingState") String shippingState,
                                               @RequestParam(required = false,value = "order.shipZip") String shipZip,
                                               @RequestParam(required = false,value = "order.shipCountry") String shipCountry,
                                               @RequestParam(required = false,value = "order.shipToFirstName") String shipToFirstName,
                                               @RequestParam(required = false,value = "order.shipToLastName") String shipToLastName) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }else {
            AccountVO loginAccount = userService.getAccountVOByUsername(username);
            OrderVO order = (OrderVO) session.getAttribute("order");
            UserAddress userAddress = new UserAddress();
            order.setShipAddress1(shippingAddress1);
            order.setShipAddress2(shippingAddress2);
            order.setShipCity(shippingCity);
            order.setShipState(shippingState);
            order.setShipZip(shipZip);
            order.setShipCountry(shipCountry);
            order.setShipToFirstName(shipToFirstName);
            order.setShipToLastName(shipToLastName);
            userAddress.setUsername(loginAccount.getUsername());
            userAddress.setAddress1(shippingAddress1);
            userAddress.setAddress2(shippingAddress2);
            userAddress.setCity(shippingCity);
            userAddress.setState(shippingState);
            userAddress.setZip(shipZip);
            userAddress.setCountry(shipCountry);
            userAddress.setFirstName(shipToFirstName);
            userAddress.setLastName(shipToLastName);
            userAddress.setStatus("OK");
            userService.addUserAddress(userAddress);
            return CommonResponse.createForSuccess(order);
        }
    }*/

    /*@PutMapping("/orders")
    @ResponseBody
    public CommonResponse<Object> addNewOrder(@RequestBody OrderVO order,
                                              @RequestHeader("Authorization") String authHeader,
                                              @RequestParam(required = false,value = "order.billAddress1") String billAddress1,
                                              @RequestParam(required = false,value = "order.billAddress2") String billAddress2,
                                              @RequestParam(required = false,value = "order.billCity") String billCity,
                                              @RequestParam(required = false,value = "order.billState") String billState,
                                              @RequestParam(required = false,value = "order.billZip") String billZip,
                                              @RequestParam(required = false,value = "order.billCountry") String billCountry,
                                              @RequestParam(required = false,value = "order.billToFirstName") String billToFirstName,
                                              @RequestParam(required = false,value = "order.billToLastName") String billToLastName,
                                              @RequestParam(required = false,value = "order.cardType") String cardType,
                                              @RequestParam(required = false,value = "order.creditCard") String creditCard,
                                              @RequestParam(required = false,value = "order.expiryDate") String expiryDate){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }else {
            order.setCardType(cardType);
            order.setCreditCard(creditCard);
            order.setExpiryDate(expiryDate);
            order.setBillAddress1(billAddress1);
            order.setBillAddress2(billAddress2);
            order.setBillCity(billCity);
            order.setBillState(billState);
            order.setBillZip(billZip);
            order.setBillCountry(billCountry);
            order.setBillToFirstName(billToFirstName);
            order.setBillToLastName(billToLastName);
            return CommonResponse.createForSuccess(order);
        }
    }*/

    @PostMapping("/orders")
    @ResponseBody
    public CommonResponse<Object> placeOrder(@RequestBody OrderVO order,
                                             @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }else {
            AccountVO loginAccount = userService.getAccountVOByUsername(username);
            Date date = new Date();
            order.setOrderDate(date);
            System.out.println(date);
            order.setOrderId(orderService.getNextOrderId());
            order.setLocale("CA");
            order.setStatus("P");
            System.out.println(order.getUsername());
            System.out.println(order.getBillAddress1());
            orderService.decreaseItemQuantity(order);
            orderService.insertOrder(order);
            userService.deleteCart(loginAccount.getUsername());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentDate = formatter.format(date);
            String addOrderString = "User "+ loginAccount.getUsername() + " added a new order "
                    + "<a href= \"viewOrder?orderId=" + order.getOrderId() + "\">"
                    + order.getOrderId() + "</a >.";
            userService.updateJournal(loginAccount.getUsername(), addOrderString, currentDate, "#ED7D31");
            return CommonResponse.createForSuccess(order);
        }
    }



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

    /*@PutMapping("/orders/addresses/{addressId}")
    @ResponseBody
    public CommonResponse<Object> changeAddress(@PathVariable("addressId") String addressId,
                                     HttpSession session){
        AccountVO loginAccount =(AccountVO) session.getAttribute("loginAccount");
        if (loginAccount == null) {
            return CommonResponse.createForError("Please log in first");
        }
        OrderVO order = (OrderVO) session.getAttribute("order");
        UserAddress userAddress = userService.getUserAddressByAddressId(loginAccount.getUsername(),addressId);
        System.out.println(userAddress);
        order.setShipAddress1(userAddress.getAddress1());
        order.setShipAddress2(userAddress.getAddress2());
        order.setShipCity(userAddress.getCity());
        order.setShipState(userAddress.getState());
        order.setShipZip(userAddress.getZip());
        order.setShipCountry(userAddress.getCountry());
        order.setShipToFirstName(userAddress.getFirstName());
        order.setShipToLastName(userAddress.getLastName());
        session.setAttribute("order", order);
        return CommonResponse.createForSuccess(userAddress);
    }*/
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


    /*@GetMapping("/returnOrder")
    public String returnOrder(String orderId, Model model)
    {
        model.addAttribute("orderId", orderId);
        return "order/returnOrder";
    }*/

    @DeleteMapping("/orders/{orderId}")
    @ResponseBody
    public CommonResponse<Object> sendReturnRequest(@PathVariable("orderId") String orderId,
                                                    @RequestHeader("Authorization") String authHeader,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("reason") String reason,
                                                    @RequestParam("image") MultipartFile image){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return CommonResponse.createForError("Please log in first");
        }else {
            String imagePath = null;
            if (!image.isEmpty()) {
                try {
                    String uploadDir = System.getProperty("user.dir") + "/../Images/";
                    System.out.println(uploadDir);
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs(); // 创建目录
                    }

                    String fileName = orderId + ".jpg";
                    File destFile = new File(uploadDir, fileName);
                    image.transferTo(destFile);

                    //存储图片路径
                    imagePath = "/../Images/" + fileName;

                } catch (IOException e) {
                    e.printStackTrace();
                    return CommonResponse.createForError("Image upload failed!");
                }
            }

            orderService.insertReturnOrder(orderId, reason, description, imagePath);

            orderService.updateStatus(orderId);
            String msg = "The return request has been sent and is waiting to be reviewed by the merchant.";
            AccountVO loginAccount = userService.getAccountVOByUsername(username);
            List<OrderVO> orderVOList = orderService.getOrdersByUsername(loginAccount.getUsername());
            return CommonResponse.createForSuccess(msg,orderVOList);
        }
    }
    /*@PostMapping("/sendReturnRequest")
    public String sendReturnRequest(String orderId, String reason, String description, MultipartFile image, Model model)
    {
        String imagePath = null;
        if (!image.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/../Images/";
                System.out.println(uploadDir);
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs(); // 创建目录
                }

                String fileName = orderId + ".jpg";
                File destFile = new File(uploadDir, fileName);
                image.transferTo(destFile);

                //存储图片路径
                imagePath = "/../Images/" + fileName;

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("msg", "Image upload failed!");
                return "order/listOrder"; // 返回订单页面
            }
        }

        orderService.insertReturnOrder(orderId, reason, description, imagePath);

        orderService.updateStatus(orderId);
        String msg = "The return request has been sent and is waiting to be reviewed by the merchant.";
        model.addAttribute("msg", msg);

        AccountVO loginAccount =(AccountVO) model.asMap().get("loginAccount");
        List<OrderVO> orderVOList = orderService.getOrdersByUsername(loginAccount.getUsername());
        model.addAttribute("orderList", orderVOList);

        return "order/listOrder";
    }*/
}
