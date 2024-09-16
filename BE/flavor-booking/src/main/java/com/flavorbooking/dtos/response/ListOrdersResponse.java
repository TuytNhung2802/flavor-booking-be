package com.flavorbooking.dtos.response;

import com.flavorbooking.models.Order;

import java.util.List;

public class ListOrdersResponse {
    private Integer error;
    private String message;
    private Integer ordersQuantity;
    private Integer totalPages;
    private List<Order> listOrders;
    private Integer status;

    public ListOrdersResponse() {
    }

    public ListOrdersResponse(Integer error, String message, Integer ordersQuantity, Integer totalPages, List<Order> listOrders, Integer status) {
        this.error = error;
        this.message = message;
        this.ordersQuantity = ordersQuantity;
        this.totalPages = totalPages;
        this.listOrders = listOrders;
        this.status = status;
    }

    public ListOrdersResponse(Integer error, String message, Integer ordersQuantity, Integer status) {
        this.error = error;
        this.message = message;
        this.ordersQuantity = ordersQuantity;
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getOrdersQuantity() {
        return ordersQuantity;
    }

    public void setOrdersQuantity(Integer ordersQuantity) {
        this.ordersQuantity = ordersQuantity;
    }

    public List<Order> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<Order> listOrders) {
        this.listOrders = listOrders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
