package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    public static final String _userName = "DaVal";
    private final UserRepository userRepo = mock(UserRepository.class);
    private final OrderRepository orderRepo = mock(OrderRepository.class);

    @Before
    public void setUp(){
        orderController = new OrderController(userRepo, orderRepo);
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);
    }

    @Test
    public void testSubmit(){
        User user =new User();
        user.setUsername(_userName);
        Item item=new Item(0L, "Round Widget", new BigDecimal("2.99"), "A widget is round");
        Cart cart=new Cart();
        cart.setId(0L);
        List<Item> itemList=new ArrayList<>(); itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99")); cart.setUser(user); user.setCart(cart);

        when(userRepo.findByUsername(_userName)).thenReturn(user);

        ResponseEntity<UserOrder> response=orderController.submit(_userName);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder order = response.getBody();
        assertNotNull(order);
        User obtainedUser = order.getUser();

        assertEquals(user, obtainedUser);
        assertEquals(itemList, order.getItems());
        assertEquals(new BigDecimal("2.99"), order.getTotal());
    }

    @Test
    public void testGetOrdersForUser(){
        User user =new User();
        user.setUsername(_userName);
        Item item=new Item(0L, "Round Widget", new BigDecimal("2.99"), "A widget is round");
        Cart cart=new Cart();
        cart.setId(0L);
        List<Item> itemList=new ArrayList<>(); itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99")); cart.setUser(user); user.setCart(cart);

        when(userRepo.findByUsername(_userName)).thenReturn(user);

        orderController.submit(_userName);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(_userName);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<UserOrder> obtainedOrders = response.getBody();
        assertNotNull(obtainedOrders);

    }
}
