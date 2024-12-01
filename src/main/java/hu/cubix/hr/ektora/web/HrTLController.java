package hu.cubix.hr.ektora.web;

import hu.cubix.hr.ektora.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

}
