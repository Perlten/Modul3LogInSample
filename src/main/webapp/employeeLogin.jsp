<%-- 
    Document   : employeeLogin
    Created on : May 8, 2018, 12:31:40 PM
    Author     : Perlt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <%@include file="/WEB-INF/include/includeBootstrap.jsp" %>
        <%@include file="css/employeeLogin.jsp" %>
        <%@include file="WEB-INF/include/includeErrorBanner.jsp" %>
    </head>
    <body>
        <div class="container-fluid">
            <div class="loginStyle">
                <form action="FrontController" method="post">
                    <h1>Login</h1>
                    <div class="form-group">
                        <input type="hidden" name="command" value="LoginVerification">
                        <label class="control-label">Username</label>
                        <input type="text" class="form-control" name="username" placeholder="Username">

                        <label class="control-label">Password</label>
                        <input type="password" class="form-control" name="password" placeholder="Password">
                        <br>
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                </form>
                <form action="FrontController" method="post">
                    <input type="hidden" name="command" value="ForgotPassword">
                    <button type="submit" class="btn btn-primary">Forgot password</button>
                </form>
            </div>
            <br>
            <div>
                <div class="card" style="width: 300px">
                    <div class="card-body">
                        <p>Censor/Teacher login: admin</p>
                        <p>Censor/Teacher password: 1234</p>
                    </div>
                </div>

            </div>
        </div>
    </body>
</html>
