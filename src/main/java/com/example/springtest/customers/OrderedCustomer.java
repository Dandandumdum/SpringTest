package com.example.springtest.customers;

public class OrderedCustomer extends Customer{
    private String id;
    private String order;

    public OrderedCustomer(String id, String order){
        this.setId(id);
        this.setOrder(order);

    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }




}