<%@page import="FunctionLayer.Calculator"%>
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
        <%@include file="../include/includeBootstrap.jsp" %>
    </head>
    <body id="bodyMode">
        <%@include file="../include/includeEmployeeNav.jsp"%>
        <%
            Order selectedOrder = (Order) request.getAttribute("order");
            int loads = 0;
            if (request.getParameter("loads") != null) {
                loads = Integer.parseInt(request.getParameter("loads"));
            }

        %>

        <script>
            function applyMode() {
                var darkMode = sessionStorage.getItem("darkMode");
                if (darkMode === "true") {
                    document.getElementById("bodyMode").style = "background-color: #2E3338";
                    document.getElementById('h1Mode').style = "color: white; display: inline";
                     document.getElementById("cardBody1").className = "card bg-dark text-white";
                    document.getElementById("slider").checked = true;
                }
            }

            var num = <%= loads%>;
            function showCustomer(add) {
                if (add || num == 0) {
                    num += 10;
                }
                var xhttp = new XMLHttpRequest();
                //onreadystatechange gets called every time whenever the readyState attribute changes.
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("orders").innerHTML = this.responseText;
                    }
                };
                xhttp.open("post", "ordersAjax.jsp?num=" + num, true);
                xhttp.send();
            }
        </script>
        <div class="container-fluid">
            <div class="row">
                <div class="container-fluid">
                    <h1 id="h1Mode" style="display: inline">Orders</h1>
                    <div style="float: right">
                        <%@include file="../include/includeDarkModeSlider.jsp" %>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="card">
                        <div class="card-body bg-light text-dark" style="padding: 0px" id="cardBody1">
                            <div id="orders"></div>
                        </div>
                        <div class="card-footer" style="padding: 0px">
                            <input type="submit" onclick="showCustomer(true)" class="btn"  value="Load" style="width: 100%; margin: 0px; background-color: darkorange; color: white">
                        </div>
                    </div>
                </div>
                <% if (selectedOrder != null) {

                        String card = "card bg-default";
                        if (selectedOrder.isConfirmed()) {
                            card = "bg-success text-light";
                        }
                %>
                <div class="col-lg-6">
                    <div class="card <%= card%>">
                        <div class="card-header">
                            <div>
                                <h3>Contents of Order </h3>
                            </div>
                        </div>
                        <div class="card-body bg-light text-dark" style="padding: 0px">
                            <table class="table table">
                                <thead>
                                <th>ID</th>
                                <th>Length</th>
                                <th>Width</th>
                                <th>Height</th>
                                <th>Roof angle</th>
                                <th>Shed Length</th>
                                <th>Shed width</th>
                                </thead>
                                <%
                                    int shedLength = 0;
                                    int shedWidth = 0;

                                    if (selectedOrder.getCustomization().getShed() != null) {
                                        shedLength = selectedOrder.getCustomization().getShed().getLength();
                                        shedWidth = selectedOrder.getCustomization().getShed().getWidth();
                                    }
                                %>
                                <tbody>
                                    <tr>
                                        <td> <%= selectedOrder.getOrderid()%> </td>
                                        <td> <%= selectedOrder.getCustomization().getLength()%> </td>
                                        <td> <%= selectedOrder.getCustomization().getWidth()%> </td>
                                        <td> <%= selectedOrder.getCustomization().getHeight()%> </td>
                                        <td> <%= selectedOrder.getCustomization().getRoofangle()%> </td>
                                        <td> <%= shedLength%> </td>
                                        <td> <%= shedWidth%> </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div style="height: 300px; width: 50%; margin: auto; ">
                                <%@include file="../include/includeSVG.jsp" %>
                            </div>
                        </div>
                        <div class="card-footer bg-light text-dark" style="padding: 0px">
                            <% Calculator calc = LogicFacade.getCalculator(selectedOrder);
                                calc.calculate();

                                double productPrice = calc.totalPrice();
                                double price = selectedOrder.getPrice();
                                double diff = price - productPrice;
                            %>
                            <table class="table" style="width: 100%; margin-bottom: 0px">
                                <tbody>
                                    <tr class="text-primary">
                                        <td><p >Total Product Price</p></td>
                                        <td><p style="float: right"><%= productPrice%> DKK</p></td>
                                    </tr>
                                    <tr class="text-secondary">
                                        <td><p>
                                                <% if (diff < 0) {
                                                        out.print("-");
                                                    } else {
                                                        out.print("+");
                                                    }%>
                                            </p></td>
                                        <td><p style="float: right"><%= Math.abs(diff)%> DKK</p></td>
                                    </tr>
                                    <tr>
                                        <th><p>Total price</p></th>
                                        <th><p style="float:right"><%= price%> DKK</p></th>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <% if (!selectedOrder.isConfirmed()) {%>
                    <div style="float: left; padding: 5px">
                        <form action="FrontController" method="post">
                            <input type="hidden" name="command" value="ConfirmOrder">
                            <input type="hidden" name="orderId" value="<%= selectedOrder.getOrderid()%>">
                            <input type="hidden" name="loads" value="<%= loads%>">
                            <input type="submit" class="btn btn-success" value="Confirm">
                        </form>
                    </div>
                    <div style="float: left; padding: 5px">
                        <form action="FrontController" method="post">
                            <input type="hidden" name="command" value="EditOrderPage">
                            <input type="hidden" name="orderToEdit" value="<%= selectedOrder.getOrderid()%>">
                            <input type="submit" class="btn btn-primary" value="Edit">
                        </form>
                    </div>
                    <%} else {%>
                    <div style="float: left; padding: 5px">
                        <form action="FrontController" method="post">
                            <input type="hidden" name="command" value="Unconfirm">
                            <input type="hidden" name="orderId" value="<%= selectedOrder.getOrderid()%>">
                            <input type="hidden" name="loads" value="<%= loads%>">
                            <input type="submit" class="btn btn-primary" value="Unconfirm">
                        </form>
                    </div>
                    <%}%>
                    <div style="float: left; padding: 5px">
                        <form action="FrontController" method="post">
                            <input type="hidden" name="command" value="DeleteOrder">
                            <input type="hidden" name="orderToDelete" value="<%= selectedOrder.getOrderid()%>">
                            <input type="hidden" name="loads" value="<%= loads%>">
                            <input type="submit" class="btn btn-danger" value="Delete">
                        </form>
                    </div>
                    <div style="float: left; padding: 5px">
                        <form action="FrontController" method="post">
                            <input type="hidden" name="command" value="PickingList">
                            <input type="hidden" name="orderPicking" value="<%= selectedOrder.getOrderid()%>">
                            <input type="submit" class="btn btn-light" value="Picking List">
                        </form>
                    </div>
                </div>
                <% }%>

            </div>

        </div>

    </body>
</html>
