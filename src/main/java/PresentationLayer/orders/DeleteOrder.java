/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.orders;

import FunctionLayer.FOGException;
import FunctionLayer.LogicFacade;
import FunctionLayer.entities.Employee;
import FunctionLayer.entities.Event;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Perlt
 */
public class DeleteOrder implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FOGException {
        int orderId = Integer.parseInt(request.getParameter("orderToDelete"));

        //event
        Employee emp = (Employee) request.getSession().getAttribute("employee");
        LogicFacade.writeOrderEmployeeEvent(new Event(emp, 3, orderId));

        LogicFacade.removeOrder(orderId);
        return new GetOrdersPage().execute(request, response);
    }

}
