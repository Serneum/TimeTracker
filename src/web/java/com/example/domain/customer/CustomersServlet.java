package com.example.domain.customer;

import com.example.domain.customer.Customer;
import com.example.domain.customer.CustomerDaoSql;
import com.example.domain.tasks.BaseServlet;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomersServlet extends BaseServlet {
    private static final Logger logger = Logger.getLogger(CustomersServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
        CustomerDaoSql dao = CustomerDaoSql.getInstance();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String customerId = getSanitizedValue(req, "edit");
        if (StringUtils.isNotBlank(customerId)) {
            Customer editCustomer = dao.restoreForId(UUID.fromString(customerId));
            if (editCustomer != null) {
                req.setAttribute("inEditMode", true);
                req.setAttribute("editCustomer", editCustomer);
            }
            else {
                logger.log(Level.WARNING, "No customer with id '" + customerId + "' found.");
            }
        }

        List<Customer> customerList = dao.restoreAll();
        String error = getSanitizedValue(req, "error");
        if (StringUtils.isNotBlank(error)) {
            req.setAttribute("error", error);
        }
        req.setAttribute("user", user);
        req.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("customers.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        StringBuilder urlBuilder = new StringBuilder("/customers/");
        CustomerDaoSql dao = CustomerDaoSql.getInstance();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String err = "";
        String customerId = getSanitizedValue(req, "customerId");
        String name = getSanitizedValue(req, "name");
        String address1 = getSanitizedValue(req, "address1");
        String address2 = getSanitizedValue(req, "address2");
        String address3 = getSanitizedValue(req, "address3");
        String city = getSanitizedValue(req, "city");
        String state = getSanitizedValue(req, "state");
        String zip = getSanitizedValue(req, "zip");
        String phone1 = getSanitizedValue(req, "phone1");
        String phone2 = getSanitizedValue(req, "phone2");
        String fax1 = getSanitizedValue(req, "fax1");
        String fax2 = getSanitizedValue(req, "fax2");
        String email = getSanitizedValue(req, "email");
        String website = getSanitizedValue(req, "website");

        boolean isNew = false;
        Customer customer = null;
        if (StringUtils.isBlank(name)) {
            err = "You must enter a name for the customer.";
        }
        else {
            try {
                if (StringUtils.isBlank(customerId)) {
                    Customer existingCustomer = dao.restoreForName(name);
                    if (existingCustomer == null) {
                        // Create new customer
                        customer = new CustomerBuilder()
                                .name(name)
                                .address1(address1)
                                .address2(address2)
                                .address3(address3)
                                .city(city)
                                .state(state)
                                .zip(zip)
                                .phone1(phone1)
                                .phone2(phone2)
                                .fax1(fax1)
                                .fax2(fax2)
                                .email(email)
                                .website(website)
                                .build();
                        isNew = true;
                    }
                    else {
                        err = "A customer already exists for name '" + name + "'.";
                    }
                }
                else {
                    // Update existing customer
                    customer = dao.restoreForId(UUID.fromString(customerId));
                    customer.setName(name);
                    customer.setAddress1(address1);
                    customer.setAddress2(address2);
                    customer.setAddress3(address3);
                    customer.setCity(city);
                    customer.setState(state);
                    customer.setZip(zip);
                    customer.setPhone1(phone1);
                    customer.setPhone2(phone2);
                    customer.setFax1(fax1);
                    customer.setFax2(fax2);
                    customer.setEmail(email);
                    customer.setWebsite(website);
                }
            }
            catch (IllegalArgumentException e) {
                err = e.getMessage();
            }
        }

        if (StringUtils.isNotBlank(err)) {
            urlBuilder.append("?error=").append(err);
            req.setAttribute("error", err);
        }
        else if (isNew) {
            dao.insert(customer);
        }
        else {
            dao.update(customer);
        }
        resp.sendRedirect(urlBuilder.toString());
    }
}
