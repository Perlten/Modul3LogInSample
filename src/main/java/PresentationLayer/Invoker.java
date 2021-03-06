package PresentationLayer;

import PresentationLayer.events.GetAllEvents;
import PresentationLayer.editEmployee.CreateNewEmployee;
import PresentationLayer.editEmployee.EditEmployee;
import PresentationLayer.editEmployee.FireEmployee;
import PresentationLayer.editEmployee.ResetPassword;
import PresentationLayer.editEmployee.UpdateEmployee;
import PresentationLayer.editEmployee.UpdateStaffPage;
import PresentationLayer.events.GetAllEventsPage;
import PresentationLayer.login.EmployeeLoginPage;
import PresentationLayer.login.ForgotPasswordPage;
import PresentationLayer.login.LoginVerification;
import PresentationLayer.login.NewPassword;
import PresentationLayer.login.ResetAdmin;
import PresentationLayer.login.SendNewPassword;
import PresentationLayer.login.EmployeeOverviewPage;
import PresentationLayer.orders.ConfirmOrder;
import PresentationLayer.orders.DeleteOrder;
import PresentationLayer.orders.EditOrder;
import PresentationLayer.orders.EditOrderPage;
import PresentationLayer.orders.GetOrdersPage;
import PresentationLayer.orders.PickingListPage;
import PresentationLayer.orders.ShowOrder;
import PresentationLayer.orders.Unconfirm;
import PresentationLayer.orders.style.CreateStyle;
import PresentationLayer.orders.style.CreateStylePage;
import PresentationLayer.orders.style.DeleteStyle;
import PresentationLayer.orders.style.GetStyle;
import PresentationLayer.orders.style.UpdateStyle;
import PresentationLayer.orders.style.UpdateStylePage;
import PresentationLayer.requesting.GiveCredentials;
import PresentationLayer.requesting.GiveCredentialsPage;
import PresentationLayer.requesting.GiveDimentions;
import PresentationLayer.requesting.GiveDimentionsPage;
import PresentationLayer.requesting.GiveStyling;
import PresentationLayer.requesting.LoadOrderPage;
import PresentationLayer.requesting.StylingPage;
import PresentationLayer.requesting.SubmitOrder;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public abstract class Invoker {

    public static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

    private static HashMap<String, Command> commands;

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put("GetOrders", new GetOrdersPage());
        commands.put("ShowOrder", new ShowOrder());
        commands.put("ConfirmOrder", new ConfirmOrder());
        commands.put("DeleteOrder", new DeleteOrder());
        commands.put("EditOrderPage", new EditOrderPage());
        commands.put("EditOrder", new EditOrder());
        commands.put("UpdateStylePage", new UpdateStylePage());
        commands.put("GiveDimentions", new GiveDimentions());
        commands.put("Styling", new StylingPage());
        commands.put("GetStyle", new GetStyle());
        commands.put("UpdateStyle", new UpdateStyle());
        commands.put("CreateStylePage", new CreateStylePage());
        commands.put("CreateStyle", new CreateStyle());
        commands.put("DeleteStyle", new DeleteStyle());
        commands.put("GiveStyling", new GiveStyling());
        commands.put("GiveCredentials", new GiveCredentials());
        commands.put("Unconfirm", new Unconfirm());
        commands.put("GiveDimentionsPage", new GiveDimentionsPage());
        commands.put("GiveCredentialsPage", new GiveCredentialsPage());
        commands.put("SubmitOrder", new SubmitOrder());
        commands.put("LoadOrder", new LoadOrderPage());
        commands.put("LoginVerification", new LoginVerification());
        commands.put("SendNewPassword", new SendNewPassword());
        commands.put("Overview", new EmployeeOverviewPage());
        commands.put("UpdateStaff", new UpdateStaffPage());
        commands.put("EditEmployee", new EditEmployee());
        commands.put("UpdateEmployee", new UpdateEmployee());
        commands.put("FireEmployee", new FireEmployee());
        commands.put("ResetPassword", new ResetPassword());
        commands.put("NewPassword", new NewPassword());
        commands.put("CreateNewEmployee", new CreateNewEmployee());
        commands.put("PickingList", new PickingListPage());
        commands.put("ForgotPassword", new ForgotPasswordPage());
        commands.put("AllEvents", new GetAllEvents());
        commands.put("AllEventsPage", new GetAllEventsPage());
        commands.put("ResetAdmin", new ResetAdmin());
        commands.put("LogOut", new EmployeeLoginPage());
    }

    static Command from(HttpServletRequest request) {
        String commandName = request.getParameter("command");
        if (commands == null) {
            initCommands();
        }
        return commands.getOrDefault(commandName, new UnknownCommand());
    }

}
