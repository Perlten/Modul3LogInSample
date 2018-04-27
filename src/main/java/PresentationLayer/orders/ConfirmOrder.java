package PresentationLayer.orders;

import PresentationLayer.orders.GetOrders;
import FunctionLayer.LogicFacade;
import FunctionLayer.LoginSampleException;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmOrder extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        int orderId = Integer.parseInt(request.getParameter("orderToConfirm"));
        LogicFacade.confirmOrder(orderId);
        return new GetOrders().execute(request, response);
    }
    
}