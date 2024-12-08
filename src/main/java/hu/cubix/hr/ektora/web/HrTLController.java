package hu.cubix.hr.ektora.web;

import hu.cubix.hr.ektora.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class HrTLController {

    private List<Employee> employees;

    public HrTLController(List<Employee> employees){
        this.employees = employees;
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/employee")
    public String employeeList(Map<String,Object> model){
        model.put("employees",employees);
        model.put("newEmployee", new Employee());
        return "employee";
    }

    @PostMapping("/employee")
    public String addEmployee(Employee newEmployee){
        employees.add(newEmployee);
        return "redirect:employee";
    }

    @GetMapping("/employee/edit/{employeeID}")
    public String editEmployee(@PathVariable long employeeID, Map<String,Object> model){
        Employee employee = employees.stream()
                .filter(searchedEmployee -> searchedEmployee.getId() == employeeID)
                .findFirst()
                .orElse(new Employee());
        model.put("employee",employee);
        return "editEmployee";
    }

    @PostMapping("/employee/edit/{employeeID}")
    public String editEmployee(@PathVariable long employeeID, Employee employee){
        Employee resultEmployee = employees.stream()
                .filter(searchedEmployee -> searchedEmployee.getId() == employeeID)
                .findFirst()
                .orElse(null);
        if(resultEmployee != null){
            resultEmployee.setName(employee.getName());
            resultEmployee.setJob(employee.getJob());
            resultEmployee.setSalary(employee.getSalary());
            resultEmployee.setStartedWorking(employee.getStartedWorking());
        }
        return "redirect:/employee";
    }

    @GetMapping("/employee/delete/{employeeID}")
    public String editEmployee(@PathVariable long employeeID){
        employees.removeIf(employee -> employee.getId() == employeeID);
        return "redirect:/employee";
    }

}
