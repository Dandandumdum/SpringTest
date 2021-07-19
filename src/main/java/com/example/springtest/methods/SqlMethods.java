package com.example.springtest.methods;

import com.example.springtest.customers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SqlMethods {
    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;
    public ArrayList<Customer> selectAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId, FirstName,LastName, Country, PostalCode, Phone, Email FROM Customer");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")

                        ));
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return customers;
        }
    }
    public Customer selectSpecificCustomer(String customerId) {
        Customer customer = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId, FirstName,LastName, Country, PostalCode, Phone, Email FROM Customer WHERE CustomerId = ?");
            preparedStatement.setString(1, customerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customer = new Customer(
                        resultSet.getString("CustomerId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Country"),
                        resultSet.getString("PostalCode"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email")
                );
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return customer;
        }
    }
    //Selects specific customer from database, selected data is chosen by customerName input parameter.
    //HTTP GET
    public Customer selectSpecificCustomerByName(String customerName) {
        Customer customer = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId, FirstName,LastName, Country, PostalCode, Phone, Email FROM Customer WHERE FirstName LIKE ?");
            preparedStatement.setString(1, customerName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customer = new Customer(
                        resultSet.getString("CustomerId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Country"),
                        resultSet.getString("PostalCode"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email")
                );
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return customer;
        }
    }
    //Adds new Customer object to the database, based upon Customer object parameter
    //HTTP POST
    public Boolean addNewCustomer(Customer newCustomer) {
        Boolean success = false;

        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO Customer (CustomerId,FirstName,LastName, Country, PostalCode, Phone, Email) VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1, newCustomer.getId());
            preparedStatement.setString(2, newCustomer.getFirstName());
            preparedStatement.setString(3, newCustomer.getLastName());
            preparedStatement.setString(4, newCustomer.getCountry());
            preparedStatement.setString(5, newCustomer.getPostalCode());
            preparedStatement.setString(6, newCustomer.getPhoneNumber());
            preparedStatement.setString(7, newCustomer.getEmail());
            int result = preparedStatement.executeUpdate();
            success = (result != 0);

            System.out.println("Customer added");


        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }

        }return success;
    }
    //Updates database with a Customer Object based upon a new Customer Objects details, input into methods parameters
    //,update based upon CustomerId.
    //HTTP PUT or PATCH
    public Boolean updateCustomer( Customer newCustomerDetails) {
        Boolean success = false;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("UPDATE Customer SET CustomerId = ?, FirstName = ? ,LastName = ?, Country = ?, PostalCode = ?, Phone = ?, Email = ? WHERE CustomerId = ?");
            preparedStatement.setString(1, newCustomerDetails.getId());
            preparedStatement.setString(2, newCustomerDetails.getFirstName());
            preparedStatement.setString(3, newCustomerDetails.getLastName());
            preparedStatement.setString(4, newCustomerDetails.getCountry());
            preparedStatement.setString(5, newCustomerDetails.getPostalCode());
            preparedStatement.setString(6, newCustomerDetails.getPhoneNumber());
            preparedStatement.setString(7, newCustomerDetails.getEmail());
            preparedStatement.setString(8, newCustomerDetails.getId());
            int result = preparedStatement.executeUpdate();
            success = (result != 0);

            System.out.println("Customer updated");

        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }

        }return success;
    }
    //Orders Grouped OrderedCustomer objects based upon the how many are from a distinct country.
    //Customers are added to Arraylist orderedCustomers which is then returned.
    //HTTP GET
    public ArrayList<OrderedCustomer> orderByCountryCount() {
        ArrayList<OrderedCustomer> orderedCustomers = new ArrayList<OrderedCustomer>();
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT COUNT(CustomerId), Country FROM Customer GROUP BY Country ORDER BY COUNT(CustomerId) DESC");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderedCustomers.add(new OrderedCustomer(resultSet.getString("COUNT(CustomerId)"), resultSet.getString("Country")));

            }

        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return orderedCustomers;
        }
    }
    //Selects a subset of customers from an ArrayList of all customers. Subset is determined by a limit and an offset.
    //The offset dictates the starting index, and the limit dictates the amount of objects in the subset.
    //Customers are added to Arraylist customers which is then returned.
    //HTTP GET
    public ArrayList<Customer> selectSubsetCustomers(String limit, String offset) {
        ArrayList<Customer> customers = new ArrayList<Customer>();

        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId, FirstName,LastName, Country, PostalCode, Phone, Email  FROM Customer LIMIT ? ,?");
            preparedStatement.setInt(1, Integer.parseUnsignedInt(limit) );
            preparedStatement.setInt(2, Integer.parseInt(offset));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")

                        ));
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return customers;
        }

    }
    //Performs an INNER JOIN on Invoice from Customer to allow for a Customer object ArrayList to be sorted by Invoice.total, descending
    //Customers are added to Arraylist customers which is then returned.
    //HTTP GET
    public ArrayList<Customer> orderedCustomersHighestSpenders() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Customer.CustomerId, Customer.FirstName,Customer.LastName, Customer.Country,\n" +
                            "       Customer.PostalCode, Customer.Phone, Customer.Email ,Invoice.Total\n" +
                            "FROM Customer INNER JOIN Invoice  on Customer.CustomerId = Invoice.CustomerId\n" +
                            "ORDER BY Invoice.Total DESC");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")

                        ));
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return customers;
        }
    }
    //Performs several INNER JOINs to unify the database enabling for Customer data to be order by favourite Genre
    //HTTP GET
    public CustomerGenre specificCustomerPopularGenre(String customerId) {
        CustomerGenre customer = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT  Customer.CustomerId, Customer.FirstName,Customer.LastName, Customer.Country,\n" +
                            "       Customer.PostalCode, Customer.Phone, Customer.Email, Genre.Name\n" +
                            "FROM Customer INNER JOIN Invoice on Customer.CustomerId = Invoice.CustomerId\n" +
                            "    INNER JOIN InvoiceLine ON Invoice.InvoiceId = InvoiceLine.InvoiceId\n" +
                            "INNER JOIN Track on InvoiceLine.TrackId = Track.TrackId\n" +
                            "INNER JOIN Genre on Track.GenreId = Genre.GenreId WHERE Customer.CustomerId = ?\n" +
                            "GROUP BY Genre.Name\n" +
                            "ORDER BY COUNT(InvoiceLine.Quantity) DESC LIMIT 1");
            preparedStatement.setString(1, customerId); // Corresponds to 1st '?' (must match type)

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customer = new CustomerGenre(
                        resultSet.getString("CustomerId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Country"),
                        resultSet.getString("PostalCode"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("Name")

                );

            }
        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return customer;
        }
    }
}
