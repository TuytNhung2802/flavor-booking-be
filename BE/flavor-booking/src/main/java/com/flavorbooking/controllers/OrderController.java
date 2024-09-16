package com.flavorbooking.controllers;


import com.flavorbooking.constant.SystemConstant;
import com.flavorbooking.dtos.BillOrderDTO;
import com.flavorbooking.dtos.OrderCartDTO;
import com.flavorbooking.dtos.response.ListOrdersResponse;
import com.flavorbooking.dtos.response.ResponseMessage;
import com.flavorbooking.models.*;
import com.flavorbooking.request.CheckRequest;
import com.flavorbooking.request.OrderRequest;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.impl.*;
import com.flavorbooking.services.order.OrderServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderServiceImpl orderServices;
    @Autowired
    RestaurantTableService restaurantTableService;
    @Autowired
    DishService dishService;
    @Autowired
    AccountService customerService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    OrderItemService orderItemService;

    @PostMapping("/add-order/{uid}/{rid}")
    private ResponseEntity<?> addOrder(@RequestBody OrderRequest request, @PathVariable int rid, @PathVariable int uid){
        Order order = new Order();
        order.setDeposited(request.getDeposit());
        order.setFullName(request.getFull_name());
        order.setPhone(request.getPhone());
        order.setTimeFrom(LocalTime.parse(request.getTime_from()));
        order.setTimeTo(LocalTime.parse(request.getTime_to()));
        RestaurantTable table = restaurantTableService.getTable(Integer.parseInt(request.getTid()));
        order.setRestaurantTable(table);
        order.setPrice(request.getPrice());
        order.setProductStatus(0);
        order.setNumberPeople(Integer.parseInt(request.getNumber_people()));
        order.setDate(LocalDate.parse(request.getOrder_date()));
        Restaurant restaurant = restaurantService.findResById(rid);
        order.setRestaurant(restaurant);
        Account account = customerService.findAccount(uid);
        order.setAccount(account);
        orderService.addOrder(order);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(order);
        //Add Dish into the OrderItem
        for(int i=0; i < request.getItems().size(); i++){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuatity(Integer.parseInt(request.getItems().get(i).getQuantity()));
            Dish dish = dishService.getDish(Integer.parseInt(request.getItems().get(i).getDid()));
            orderItem.setDish(dish);
            orderItem.setOrder(order);
            orderItem.setTotal(request.getItems().get(i).getTotal());
            orderItemService.addOrderItem(orderItem);
        }
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/check-order/{rid}")
    private ResponseEntity<?> checkOrder(@RequestBody CheckRequest request, @PathVariable int rid){
        Restaurant restaurant = restaurantService.findResById(rid);
        int timeFrom = Integer.parseInt(restaurant.getTimeOpen().toString().substring(0,2));
        int timeTo = Integer.parseInt(restaurant.getTimeClose().toString().substring(0,2));
        String timeNow = request.getOrder_date();

        List<Double> inTime = new ArrayList();
        List<Double> outTime = new ArrayList();
        List<Double> clock = new ArrayList();
        List<Integer> time = new ArrayList();

        //Khoi tao cay thoi gian cho nha hang
        for(int i=timeFrom; i<timeTo; i++){
            clock.add((double) i);
            clock.add(i+0.15);
            clock.add(i+0.30);
            clock.add(i+0.45);
        }
        clock.add((double) timeTo);

        //Lay thoi gian toi -> ve cua Order co san
        List<Object[]> orders = orderService.getOrderByDate(timeNow, Integer.parseInt(request.getTid()));
        for(int i=0;i< orders.size();i++){
            String time_from = String.valueOf(orders.get(i)[0]); // Lấy thời gian bắt đầu
            String time_to = String.valueOf(orders.get(i)[1]); // Lấy thời gian kết thúc

            // Thêm vào danh sách thời gian vào và thời gian ra
            inTime.add(SystemConstant.convertTime(time_from));
            outTime.add(SystemConstant.convertTime(time_to));
        }

//        Khoi tao lich đã có Order(1) va chua có Order(0)
        for(int i=0;i< clock.size();i++) time.add(0);
        for(int i=0;i< inTime.size();i++){
            for(int j=0;j< clock.size();j++){
                if(clock.get(j) > inTime.get(i) && clock.get(j)<= outTime.get(i)){
                    time.set(j, 1);
                }
            }
        }

        //Check Order moi them vao co hop le ko
        double newFrom = SystemConstant.convertTime(request.getTime_from());
        double newTo = SystemConstant.convertTime(request.getTime_to());

        ResourceResponse response = new ResourceResponse();
        if(SystemConstant.checkTime(newFrom, newTo, time, clock) == true){
            response.setSuccess(true);
            response.setMessage("ok");
            response.setData(orders);
        }else{
            response.setSuccess(false);
            response.setMessage("Please choose another time!");
            response.setData(null);
        }

        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/manage-order/{rid}")
    private ResponseEntity<?> addOrder(@RequestParam String date, @PathVariable int rid){
        List<Order> orders = orderService.getOrderCurrentDate(date, rid);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(orders);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/bill-order/{oid}")
    private ResponseEntity<?> getBill(@PathVariable Integer oid){
        Order order = orderService.findOrderById(oid);
        List<OrderItem> orderItemsList = orderItemService.getAllOrder(oid);
        BillOrderDTO billOrderDTO = new BillOrderDTO(order, orderItemsList);

        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(billOrderDTO);

        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list-order/{uid}")
    private ResponseEntity<?> listHistoryOrder(@PathVariable Integer uid){
        List<Order> orderList = orderService.getOrderByUid(uid);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(orderList);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/order-restaurant/{rid}")
    private ResponseEntity<?> listOrderRestaurant(@PathVariable Integer rid){
        List<Order> orderList = orderService.getOrderByRid(rid);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(orderList);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/cancel-order/{oid}")
    private ResponseEntity<?> cancelOrder(@PathVariable Integer oid){
        orderService.cancelOrder(oid);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(null);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/current-order")
    private ResponseEntity<?> getOrderCurrent(){
        List<Order> data = orderService.getOrderCurrent();
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(data);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-status-order/{oid}")
    private ResponseEntity<?> updateStatusOrder(@PathVariable("oid") Integer oid, @RequestParam("status") Integer status){
        orderService.updateStatusOrder(oid, status);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(null);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("update-order-item/{oid}/{did}")
    private ResponseEntity<?> updateOrderItem(@PathVariable("oid") Integer oid,
                                              @PathVariable("did") Integer did,
                                              @PathParam("quantity") Integer quantity){
        Order order = orderService.findOrderById(oid);
        Dish dish = dishService.getDish(did);
        List<OrderItem> orderItems = new ArrayList<>();
        if(orderItemService.getOrderItemByDidAndOid(oid, did) !=null){
            orderItems = orderItemService.updateOrderItem(oid, did, quantity);
            order.setPrice(order.getPrice() + orderItemService.getOrderItemByDidAndOid(oid, did).getDish().getPrice()*quantity);
        }else{
            OrderItem orderItem = new OrderItem();
            orderItem.setQuatity(quantity);
            orderItem.setOrder(order);
            orderItem.setDish(dish);
            orderItem.setTotal(dish.getPrice()*quantity);
            orderItemService.addOrderItem(orderItem);
            orderItems = orderItemService.getAllOrder(oid);
            order.setPrice(order.getPrice() + orderItem.getQuatity()*orderItem.getDish().getPrice());
        }

        orderService.saveOrder(order);
        BillOrderDTO billOrderDTO = new BillOrderDTO(order, orderItems);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(billOrderDTO);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }
    //delete order item
    @GetMapping("delete-order-item/{oid}/{did}")
    private ResponseEntity<?> deleteOrderItemByDid(@PathVariable("oid") Integer oid,
                                              @PathVariable("did") Integer did){
        Order order = orderService.findOrderById(oid);
        OrderItem orderItem = orderItemService.getOrderItemByDidAndOid(oid, did);
        orderItemService.deleteOrderItem(orderItem.getId());
        List<OrderItem> orderItems = orderItemService.getAllOrder(oid);
        order.setPrice(order.getPrice() - orderItem.getTotal());
        orderService.saveOrder(order);

        BillOrderDTO billOrderDTO = new BillOrderDTO(order, orderItems);
        ResourceResponse response = new ResourceResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setData(billOrderDTO);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                          @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
                                          @RequestParam(defaultValue = "id", required = false, name = "sortBy") String sortBy,
                                          @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
                                          @RequestParam(required = false, name = "id") Integer id
                                        ) {
        try {
            Page<Order> ordersPage = orderServices.getAllOrders(page-1, limit, sortBy, sortDir, id);
            if (!ordersPage.isEmpty()) {
                return new ResponseEntity<>(new ListOrdersResponse(0, "Get Orders Successfully", (int) ordersPage.getTotalElements(), ordersPage.getTotalPages(), ordersPage.getContent(), 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ListOrdersResponse(1, "Order Not Found", 0, 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
}


