/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.requesting;

import FunctionLayer.LoginSampleException;
import FunctionLayer.entities.Customization;
import FunctionLayer.entities.Order;
import FunctionLayer.entities.Shed;
import PresentationLayer.Command;
import PresentationLayer.Helper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author adamlass
 */
public class GiveDimentions extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        try {
            int length = Integer.parseInt(request.getParameter("length"));
            int width = Integer.parseInt(request.getParameter("width"));
            int height = Integer.parseInt(request.getParameter("height"));

            boolean roof = Boolean.parseBoolean(request.getParameter("roof"));

            boolean shed = Boolean.parseBoolean(request.getParameter("shed"));

            double roofAngle = Helper.safeDouble(request, "roofangle");
            int shedLength = Helper.safeInt(request, "shedLength");
            int shedWidth = Helper.safeInt(request, "shedWidth");

            if (roof) {
                if (roofAngle <= 0 || roofAngle > 89) {
                    throw new IllegalArgumentException();
                }
            } else {
                roofAngle = 0;
            }

            Shed shedObj = null;
            if (shed) {
                shedObj = new Shed(shedLength, shedWidth);
            } else {
                
            }

            Customization cust = new Customization(length, width, height, roofAngle, shedObj, null, null);
            Order order = new Order(null, cust);

            request.getSession().setAttribute("order", order);
        } catch (Exception e) {
            throw new LoginSampleException("Could not submit customization!");
        }
        return "index";
    }

    

}
