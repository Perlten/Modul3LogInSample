/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.requesting;

import FunctionLayer.FOGException;
import FunctionLayer.entities.Customer;
import FunctionLayer.entities.Order;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Perlt
 */
public class GiveCredentials extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FOGException {

        try {
            Order order = (Order) request.getSession().getAttribute("order");

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));

            Customer customer = new Customer(firstName, lastName, email, phoneNumber);
            order.setCustomer(customer);

        } catch (Exception e) {
            throw new FOGException("Failed to add contact information!");
        }

        return "confirm";
    }

}
