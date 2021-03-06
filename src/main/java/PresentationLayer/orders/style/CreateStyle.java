/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.orders.style;

import FunctionLayer.FOGException;
import FunctionLayer.LogicFacade;
import FunctionLayer.entities.Employee;
import FunctionLayer.entities.Event;
import FunctionLayer.entities.StyleOption;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Perlt
 */
public class CreateStyle implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FOGException {

        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        StyleOption style = new StyleOption(name, description, price);

        if (type.equals("cladding")) {
            LogicFacade.createCladding(style);
        }
        if (type.equals("tile")) {
            LogicFacade.createTile(style);
        }

        Employee emp = (Employee) request.getSession().getAttribute("employee");
        LogicFacade.writeEmployeeEvent(new Event(emp, 11));

        return new UpdateStylePage().execute(request, response);
    }

}
