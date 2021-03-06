package PresentationLayer.editEmployee;

import FunctionLayer.FOGException;
import FunctionLayer.LogicFacade;
import FunctionLayer.entities.Employee;
import FunctionLayer.entities.Event;
import PresentationLayer.Command;
import static PresentationLayer.Invoker.PATTERN;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateEmployee implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FOGException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        int accessLevel = Integer.parseInt(request.getParameter("accessLevel"));
        String email = request.getParameter("email");

        if (!PATTERN.matcher(email).matches()) {
            throw new FOGException("Not a valid email");
        }

        int empId = Integer.parseInt(request.getParameter("employeeId"));
        Employee selectedEmp = LogicFacade.getEmployeeById(empId);

        selectedEmp.setFirstname(firstName);
        selectedEmp.setLastname(lastName);
        selectedEmp.setUsername(username);
        selectedEmp.setAuthenticationLevel(accessLevel);
        selectedEmp.setEmail(email);

        LogicFacade.UpdateEmployee(selectedEmp);

        Employee emp = (Employee) request.getSession().getAttribute("employee");
        LogicFacade.writeEmployeeEvent(new Event(emp, 8));
        return new EditEmployee().execute(request, response);
    }

}
