/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.login;

import FunctionLayer.FOGException;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author adamlass
 */
public class ForgotPasswordPage implements Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FOGException {
        return "WEB-INF/employeeLogin/forgotPassword";
    }
    
}
