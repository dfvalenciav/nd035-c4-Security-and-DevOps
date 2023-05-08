package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController(itemRepo);
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void getItems (){
        Item item0 = new Item(0L, "Round Widget", new BigDecimal("2.99"), "A widget is round");
        Item item1 = new Item(1L, "Square Widget", new BigDecimal("1.99"),"A widget that is square");
        List<Item> itemList = new ArrayList<>();
        itemList.add(0, item0);
        itemList.add(1, item1);
        when(itemRepo.findAll()).thenReturn(itemList);

        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<Item> resultItems = responseEntity.getBody();
        assertNotNull(resultItems);
        assertEquals(item0, resultItems.get(0));
        assertEquals(item1, resultItems.get(1));
    }

    @Test
    public void testGetItemById(){
        Item item1=new Item(1L, "Square Widget", new BigDecimal("1.99"),"A widget that is square");
        when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Item obtainedItem= response.getBody();
        Assertions.assertNotNull(obtainedItem);
        Assertions.assertEquals(item1, obtainedItem);
    }

    @Test
    public void testGetItemByName(){
        Item item1=new Item(1L, "Square Widget", new BigDecimal("1.99"),"A widget that is square");
        List<Item> itemList= new ArrayList<>(); itemList.add(item1);
        when(itemRepo.findByName("Square Widget")).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Square Widget");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        List<Item> obtainedItems= response.getBody();
        Assertions.assertNotNull(obtainedItems);
        Assertions.assertEquals(item1, obtainedItems.get(0));
    }
}
