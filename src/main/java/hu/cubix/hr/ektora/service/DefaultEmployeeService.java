package hu.cubix.hr.ektora.service;

import hu.cubix.hr.ektora.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService implements EmployeeService{

    @Override
    public int getPayRaisePercent(Employee employee) {
        return 5;
    }
}
