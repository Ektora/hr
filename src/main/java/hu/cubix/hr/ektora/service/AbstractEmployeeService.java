package hu.cubix.hr.ektora.service;

import hu.cubix.hr.ektora.model.Employee;
import hu.cubix.hr.ektora.model.EmployeeDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractEmployeeService implements EmployeeService{

    private Map<Long, Employee> employeesMap;

    public  AbstractEmployeeService(){
        employeesMap = new HashMap<>();
        employeesMap.put(1L,new Employee((long) 1, "Zsolt", "IT szakértő", 600_000, LocalDateTime.of(2017, 1, 1, 0, 0)));
        employeesMap.put(2L,new Employee((long) 2, "Tibor", "IT csoportvezető", 550_000, LocalDateTime.of(2022, 3, 1, 0, 0)));
        employeesMap.put(3L,new Employee((long) 3, "Kristóf", "IT technikus", 450_000, LocalDateTime.of(2023, 9, 1, 0, 0)));
        employeesMap.put(4L,new Employee((long) 4, "Krisztián", "IT vezető", 900_000, LocalDateTime.of(2003, 5, 1, 0, 0)));

    }

    public List<Employee> getEmployees(Integer salary){
        if(salary != null){
            return employeesMap.values().stream()
                    .filter(employee -> employee.getSalary() >= salary)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(employeesMap.values());
    }

    public Employee getById(long id){
        return employeesMap.get(id);
    }

    public Employee createEmployee(Employee employee){
        employeesMap.put(employee.getId(),employee);
        return employee;
    }

    public Employee modifyEmployee(long id, Employee employee){
        if(!employeesMap.containsKey(id)){
            return null;
        }
        employee.setId(id);
        employeesMap.put(id,employee);
        return employee;
    }

    public void deleteEmployee(long id){
        employeesMap.remove(id);
    }

}
