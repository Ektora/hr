package hu.cubix.hr.ektora.service;

import hu.cubix.hr.ektora.model.Company;
import hu.cubix.hr.ektora.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class CompanyService {
    private HashMap<Long, Company> companies;

    public CompanyService(){
        companies = new HashMap<>();
        companies.put(1L, new Company(1L, "0000001", "Mézga Géza Kft.", "1000 Budapest", new ArrayList<Employee>(
                Arrays.asList(
                        new Employee((long) 1, "Zsolt", "IT szakértő", 600_000, LocalDateTime.of(2017, 1, 1, 0, 0)),
                        new Employee((long) 2, "Tibor", "IT csoportvezető", 550_000, LocalDateTime.of(2022, 3, 1, 0, 0))
                ))));
        companies.put(2L, new Company(2L, "0000002", "Arany Madzag Kft.", "4644 Mándok", new ArrayList<Employee>(
                Arrays.asList(
                        new Employee((long) 1, "Kristóf", "IT technikus", 450_000, LocalDateTime.of(2023, 9, 1, 0, 0)),
                        new Employee((long) 2, "Krisztián", "IT vezető", 900_000, LocalDateTime.of(2003, 5, 1, 0, 0))

                )
        )));
        companies.put(3L, new Company(3L, "0000003", "Sok A Dalom Bt.", "4625 Záhony", new ArrayList<Employee>()));

    }

    @GetMapping
    public List<Company> getCompanies(boolean full) {
        if(!full) {
            return companies.values().stream()
                    .map(company -> copyCompanies(company.getId(), company))
                    .toList();
        }
        return new ArrayList<>(companies.values());
    }

    public Company getCompany(boolean full , long id) {
        if (companies.containsKey(id)) {
            if(!full){
                return copyCompanies(id, companies.get(id));
            }
            return companies.get(id);
        }
        return null;
    }

    private Company copyCompanies(long id, Company company){
        return new Company(id,companies.get(id).getRegistrationNumber(), companies.get(id).getName(), companies.get(id).getAddress(), new ArrayList<Employee>());
    }


    public Company addCompany(Company company) {
        companies.put(company.getId(), company);
        return company;
    }


    public Company modifyCompany(long id, Company company) {
        if (!companies.containsKey(id))
           return null;
        company.setId(id);
        companies.put(id, company);
        return company;
    }


    public void deleteCompany(long id) {
        companies.remove(id);
    }

    // region Employee adding


    public Company addEmployeeToCompany(long companyId, Employee employee){
        if(!companies.containsKey(companyId))
            return null;
        Company company = companies.get(companyId);
        company.getEmployeeList().add(employee);
        return company;
    }

    public Company exchangeFullEmployeeList(long companyId, List<Employee> employeeList){
        if(!companies.containsKey(companyId))
            return null;
        Company company = companies.get(companyId);
        company.setEmployeeList(employeeList);
        return company;
    }

    public Company deleteEmployeeFromCompany(long companyId, long employeeId){
        if(!companies.containsKey(companyId))
            return null;
        companies.get(companyId).getEmployeeList().removeIf(employee -> employee.getId() == employeeId);
        return companies.get(companyId);
    }

}
