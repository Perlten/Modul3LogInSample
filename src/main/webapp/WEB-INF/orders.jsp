<%-- 
    Document   : orders
    Created on : 26-04-2018, 08:48:00
    Author     : adamlass
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="FunctionLayer.entities.Customer"%>
<%@page import="java.util.List"%>
<%@page import="FunctionLayer.entities.Order"%>
<%@page import="FunctionLayer.LogicFacade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Orders</title>
        <%@include file="../bootstrap.jsp" %>
        <% List<Order> orders = (List<Order>) session.getAttribute("orders"); %>
    </head>
    <body>
        <h1>Customer Orders</h1>

        <div class="row">
            <div class="col-lg-6">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Order Number</th>
                            <th>Email</th>
                            <th>Date</th>
                            <th>Name</th>
                            <th>Phonenumber</th>
                        </tr>
                    </thead>

                    <% for (Order order : orders) {
                            Customer cust = order.getCustomer();
                    %>
                    <tbody>
                        <tr <% if (order.isConfirmed()) { %>
                            class="success"
                            <%}%>>
                            <td><%= order.getOrderid()%></td>
                            <td><%= cust.getEmail()%></td>
                            <% SimpleDateFormat sp = new SimpleDateFormat("dd/MM/YYYY HH:mm");%>
                            <td><%= sp.format(order.getDate().getTime())%></td>
                            <td><%= cust.getLastname() + ", " + cust.getFirstname()%></td>
                            <td><%= cust.getPhonenumber()%></td>
                        </tr>
                    </tbody>
                    <% }%>

                </table>
            </div>
        </div>
    </body>
</html>