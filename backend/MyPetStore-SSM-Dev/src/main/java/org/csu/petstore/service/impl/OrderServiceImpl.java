package org.csu.petstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.petstore.entity.*;
import org.csu.petstore.persistence.*;
import org.csu.petstore.service.CatalogService;
import org.csu.petstore.service.OrderService;
import org.csu.petstore.service.UserService;
import org.csu.petstore.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private LineItemMapper lineItemMapper;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ReturnOrdersMapper returnOrdersMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UserService userService;


    @Override
    public Sequence getSequence(String name) {
        return sequenceMapper.selectById(name);
    }

    @Override
    public void updateSequence(Sequence sequence) {
        sequenceMapper.updateById(sequence);
    }

    @Override
    public int getNextOrderId() {
        Sequence sequence = getSequence("ordernum");
        Sequence nextSequence = getSequence("ordernum");
        nextSequence.setNextId(nextSequence.getNextId() + 1);
        sequenceMapper.updateById(nextSequence);
        return nextSequence.getNextId();
    }

    @Override
    public List<OrderVO> getOrdersByUsername(String username) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username);
        List<Orders> ordersList = ordersMapper.selectList(queryWrapper);
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Orders order : ordersList) {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrders(order);
            orderVO.setStatus(orderStatusMapper.selectById(order.getOrderId()).getStatus());
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public OrderVO getOrderWithLineItem(int orderId) {
        OrderVO orderVO = new OrderVO();
        orderVO.setOrders(ordersMapper.selectById(orderId));
        orderVO.setStatus(orderStatusMapper.selectById(orderId).getStatus());
        QueryWrapper<LineItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderid", orderId);
        List<LineItem> lineItemList = lineItemMapper.selectList(queryWrapper);
        for (LineItem lineItem : lineItemList) {
            ItemVO itemVO = catalogService.getItem(lineItem.getItemId());
            LineItemVO lineItemVO= new LineItemVO();
            lineItemVO.setItem(itemVO);
            lineItemVO.setLineItem(lineItem);
            orderVO.addLineItem(lineItemVO);
        }
        return orderVO;
    }


    @Override
    public void insertOrder(OrderVO order) {
        System.out.println("insert order");
        System.out.println(order.getOrders().getOrderId());
        ordersMapper.insert(order.getOrders());
        orderStatusMapper.insert(order.getOrderStatus());
        List<CartItemVO> cartItemVOList = userService.getCart(order.getUsername()).getItemList();
        int i = 1;
        for (CartItemVO cartItemVO : cartItemVOList) {
            LineItem lineItem = new LineItem();
            lineItem.setItemId(cartItemVO.getItem().getItemId());
            lineItem.setQuantity(cartItemVO.getQuantity());
            lineItem.setLineNumber(i);
            i++;
            lineItem.setUnitPrice(cartItemVO.getItem().getListPrice());
            lineItem.setOrderId(order.getOrderId());
            lineItemMapper.insert(lineItem);
        }
        System.out.println("insert order");
    }

    @Override
    public void updateStatus(String orderId)
    {
        OrderStatus orderStatus = orderStatusMapper.selectById(orderId);
        orderStatus.setStatus("W");
        orderStatusMapper.updateById(orderStatus);
    }

    @Override
    public void insertReturnOrder(String orderId, String reason, String description, String image)
    {
        ReturnOrders returnOrder = new ReturnOrders();
        returnOrder.setOrderId(orderId);
        returnOrder.setReason(reason);
        returnOrder.setDescn(description);
        returnOrder.setImage(image);
        returnOrder.setStatus("W");

        returnOrdersMapper.insert(returnOrder);
    }

    @Override
    public void decreaseItemQuantity(OrderVO order) {
        List<LineItemVO> lineItemList = order.getLineItems();
        for (LineItemVO lineItemVO : lineItemList) {
            UpdateWrapper<Inventory> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("itemid", lineItemVO.getItemId());
            updateWrapper.setSql("qty = qty - " + lineItemVO.getQuantity());
            inventoryMapper.update(null, updateWrapper);
        }

    }

    @Override
    public String checkItemQuantity(CartVO cart) {
        List<CartItemVO> cartItemList = cart.getItemList();
        for (CartItemVO cartItemVO : cartItemList) {
            int quantity = inventoryMapper.selectById(cartItemVO.getItem().getItemId()).getQuantity();
            if (quantity < cartItemVO.getQuantity())
                return cartItemVO.getItem().getItemId();
        }
        return null;
    }

    @Override
    public String checkModifying(CartVO cart) {
        List<CartItemVO> cartItemList = cart.getItemList();
        for (CartItemVO cartItemVO : cartItemList) {
            int productModifying = productMapper.selectById(cartItemVO.getItem().getProductId()).getModifying();
            int itemModifying = itemMapper.selectById(cartItemVO.getItem().getItemId()).getModifying();
            if(productModifying == 1)
                return cartItemVO.getItem().getProductId();
            if(itemModifying == 1)
                return cartItemVO.getItem().getItemId();
        }
        return null;
    }
}
