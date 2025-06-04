package org.csu.petstore.service;

import org.csu.petstore.entity.Sequence;
import org.csu.petstore.vo.CartVO;
import org.csu.petstore.vo.OrderVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface OrderService {
    Sequence getSequence(String name);
    void updateSequence(Sequence sequence);
    int getNextOrderId();

    List<OrderVO> getOrdersByUsername(String username);

    OrderVO getOrderWithLineItem(int orderId);

    void insertOrder(OrderVO order);

    void updateStatus(String orderId);

    void insertReturnOrder(String orderId, String reason, String description, String image);

    void decreaseItemQuantity(OrderVO order);
    String checkItemQuantity(CartVO cart);

    String checkModifying(CartVO cart);
}
