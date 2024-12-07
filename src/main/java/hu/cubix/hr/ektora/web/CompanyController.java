package hu.cubix.hr.ektora.web;

import hu.cubix.hr.ektora.model.CompanyDTO;
import hu.cubix.hr.ektora.model.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private HashMap<Long, CompanyDTO> companies;

    public CompanyController() {
        companies = new HashMap<>();
        companies.put(1L, new CompanyDTO(1L, "0000001", "Mézga Géza Kft.", "1000 Budapest", Arrays.asList(
                new EmployeeDTO((long) 1, "Zsolt", "IT szakértő", 600_000, LocalDateTime.of(2017, 1, 1, 0, 0)),
                new EmployeeDTO((long) 2, "Tibor", "IT csoportvezető", 550_000, LocalDateTime.of(2022, 3, 1, 0, 0))
        )));
        companies.put(2L, new CompanyDTO(2L, "0000002", "Arany Madzag Kft.", "4644 Mándok", new ArrayList<EmployeeDTO>(
                Arrays.asList(
                        new EmployeeDTO((long) 1, "Kristóf", "IT technikus", 450_000, LocalDateTime.of(2023, 9, 1, 0, 0)),
                        new EmployeeDTO((long) 2, "Krisztián", "IT vezető", 900_000, LocalDateTime.of(2003, 5, 1, 0, 0))

                )
        )));
        companies.put(3L, new CompanyDTO(3L, "0000003", "Sok A Dalom Bt.", "4625 Záhony", new ArrayList<EmployeeDTO>()));
    }

    @GetMapping
    public List<CompanyDTO> getCompanies(@RequestParam(required = false) boolean full) {
        List<CompanyDTO> resultCompanies = new ArrayList<>();
        if(full == false) {
            for (Long id : companies.keySet()) {
                resultCompanies.add(new CompanyDTO(id, companies.get(id).getRegistrationNumber(), companies.get(id).getName(), companies.get(id).getAddress(), new ArrayList<EmployeeDTO>()));
            }
            return resultCompanies;
        }
        return new ArrayList<>(companies.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@RequestParam(required = false) boolean full ,@PathVariable long id) {
        if (companies.containsKey(id)) {
            if(full == false){
                return ResponseEntity.ok(new CompanyDTO(id,companies.get(id).getRegistrationNumber(), companies.get(id).getName(), companies.get(id).getAddress(), new ArrayList<EmployeeDTO>()));
            }
            return ResponseEntity.ok(companies.get(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public CompanyDTO addCompany(@RequestBody CompanyDTO companyDTO) {
        companies.put(companyDTO.getId(), companyDTO);
        return companyDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> modifyCompany(@PathVariable long id, @RequestBody CompanyDTO companyDTO) {
        if (!companies.containsKey(id))
            return ResponseEntity.notFound().build();
        companyDTO.setId(id);
        companies.put(id, companyDTO);
        return ResponseEntity.ok(companyDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companies.remove(id);
    }

    // region Employee adding

    @PostMapping("/{companyId}/add")
    public CompanyDTO addEmployeeToCompany(@PathVariable long companyId, @RequestBody EmployeeDTO employeeDTO){
        CompanyDTO companyDTO = companies.get(companyId);
        companyDTO.getEmployeeDTOList().add(employeeDTO);
        return companyDTO;
    }

    @PostMapping("/{companyId}/exchange")
    public CompanyDTO exchangeFullEmployeeList(@PathVariable long companyId, @RequestBody List<EmployeeDTO> employeeDTOList){
        CompanyDTO companyDTO = companies.get(companyId);
        companyDTO.setEmployeeDTOList(employeeDTOList);
        return companyDTO;
    }

    @DeleteMapping("/{companyId}/delete")
    public void deleteEmployeeFromCompany(@PathVariable long companyId, @RequestParam(required = true) long employeeId){
        companies.get(companyId).getEmployeeDTOList().removeIf(employeeDTO -> employeeDTO.getId() == employeeId);
    }
    // endregion
}
