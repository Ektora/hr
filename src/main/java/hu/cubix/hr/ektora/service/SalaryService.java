package hu.cubix.hr.ektora.service;

import hu.cubix.hr.ektora.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private EmployeeService employeeService;

    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int getFinalSalary(Employee employee){
       return (int)(employee.getSalary() / 100.0 * (100 + employeeService.getPayRaisePercent(employee)));
    }

}
