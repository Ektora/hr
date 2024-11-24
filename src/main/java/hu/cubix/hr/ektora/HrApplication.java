package hu.cubix.hr.ektora;

import hu.cubix.hr.ektora.model.Employee;
import hu.cubix.hr.ektora.service.DefaultEmployeeService;
import hu.cubix.hr.ektora.service.EmployeeService;
import hu.cubix.hr.ektora.service.SalaryService;
import hu.cubix.hr.ektora.service.SmartEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    SalaryService salaryService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class,args);
    }


    @Override
    public void run(String... args) throws Exception {

        List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee((long) 1, "Zsolt", "IT szakértő", 600_000, LocalDateTime.of(2017, 1, 1, 0, 0)),
            new Employee((long) 2, "Tibor", "IT csoportvezető", 550_000, LocalDateTime.of(2022, 3, 1, 0, 0)),
            new Employee((long) 3, "Kristóf", "IT technikus", 450_000, LocalDateTime.of(2023, 9, 1, 0, 0)),
            new Employee((long) 4, "Krisztián", "IT vezető", 900_000, LocalDateTime.of(2003, 5, 1, 0, 0))
        ));

        System.out.println(salaryService.getFinalSalary(employees.get(0)));
        System.out.println(salaryService.getFinalSalary(employees.get(1)));
        System.out.println(salaryService.getFinalSalary(employees.get(2)));
        System.out.println(salaryService.getFinalSalary(employees.get(3)));
    }


}
