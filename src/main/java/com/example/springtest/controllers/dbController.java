package com.example.springtest.controllers;
import com.example.springtest.customers.Customer;
import com.example.springtest.customers.CustomerGenre;
import com.example.springtest.customers.OrderedCustomer;
import com.example.springtest.methods.SqlMethods;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@CrossOrigin
@RestController
public class dbController {
    private SqlMethods sqlMethods = new SqlMethods();

    @RequestMapping(value = "/api/getCustomerById/{id}", method = RequestMethod.GET) //Must rework endpoints according to spec
    public String searchByCustomerId(@PathVariable String id){
        Customer customer = sqlMethods.selectSpecificCustomer(id);
        return  customerToString(customer);
    }

    @RequestMapping(value ="/api/getCustomerByName/{firstName}" , method = RequestMethod.GET)
    public String searchByCustomerName(@PathVariable String firstName){
        Customer customer = sqlMethods.selectSpecificCustomerByName(firstName);
        return customerToString(customer);
    }
    @RequestMapping(value ="/api/getCustomers" , method = RequestMethod.GET)
    public ArrayList<Customer> showAllCustomers(){
        ArrayList <Customer>customers = sqlMethods.selectAllCustomers();
        return customers;
    }
    @RequestMapping(value ="/api/getCustomersGroupByCountry" , method = RequestMethod.GET)

    public ArrayList<OrderedCustomer> showOrderedCustomersByCountry() {
        ArrayList<OrderedCustomer>customers = sqlMethods.orderByCountryCount();
        return customers;
    }
    @RequestMapping(value ="/api/getCustomersOrderedBySpending" , method = RequestMethod.GET)
    public ArrayList<Customer> showCustomersOrderedBySpending() {
        ArrayList<Customer> customers = sqlMethods.orderedCustomersHighestSpenders();
        return customers;

    }
    @RequestMapping(value ="/api/getSubsetCustomers/{limit}/{offset}" , method = RequestMethod.GET)
    public ArrayList<Customer> selectSubsetCustomers(@PathVariable String limit,@PathVariable String offset) {
        ArrayList<Customer> customers = sqlMethods.selectSubsetCustomers(limit,offset);
        return customers;

    }
    @RequestMapping(value ="/api/getCustomerByIdShowFavouriteGenre" , method = RequestMethod.GET)
    public String searchByCustomerNameFavouriteGenre(@RequestParam("query")String terms){
        CustomerGenre customer = sqlMethods.specificCustomerPopularGenre(terms);
        return customerToStringGenre(customer);
    }
    @RequestMapping(value="/api/addCustomer", method = RequestMethod.POST)
    public Boolean PostNewCustomer(@RequestBody Customer customer  ){
        return sqlMethods.addNewCustomer(customer);
    }
    @RequestMapping(value="/api/updateCustomer/{id}", method = RequestMethod.PUT)
    public Boolean updateCustomer(@PathVariable String id, @RequestBody Customer customer ){
        return sqlMethods.updateCustomer(customer);
    }






    public String customerToString(Customer customer){
        return "Customer Id: " + customer.getId()+ "\nCustomer First Name: " + customer.getFirstName() + "\nCustomer Last Name: " +
                customer.getLastName() + "\nCustomer Country: " +customer.getCountry() +
                 "\nCustomer Postal Code: " +customer.getPostalCode() + "\nCustomer Phone Number: " + customer.getPhoneNumber()
                + "\nCustomer Email: " +customer.getEmail();
    }
    public String customerToStringGenre(CustomerGenre customer){
        return "Customer Id: " + customer.getId()+ "\nCustomer First Name: " + customer.getFirstName() + "\nCustomer Last Name: " +
                customer.getLastName() + "\nCustomer Country: " +customer.getCountry() +
                "\nCustomer Postal Code: " +customer.getPostalCode() + "\nCustomer Phone Number: " + customer.getPhoneNumber()
                + "\nCustomer Email: " +customer.getEmail() + " Favourite Genre: "+ customer.getGenre();
    }


}
