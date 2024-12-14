package hu.cubix.hr.ektora.web;

import hu.cubix.hr.ektora.mapper.CompanyMapper;
import hu.cubix.hr.ektora.mapper.EmployeeMapper;
import hu.cubix.hr.ektora.model.CompanyDTO;
import hu.cubix.hr.ektora.model.EmployeeDTO;
import hu.cubix.hr.ektora.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    CompanyService companyService;

    @Autowired
    EmployeeMapper employeeMapper;

    public CompanyController() {
        }

    @GetMapping
    public List<CompanyDTO> getCompanies(@RequestParam(required = false) boolean full) {
        return companyMapper.companiesToDtos(companyService.getCompanies(full));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@RequestParam(required = false) boolean full ,@PathVariable long id) {
        CompanyDTO companyDTO = companyMapper.companyToDto(companyService.getCompany(full,id));
        if(companyDTO != null){
            return ResponseEntity.ok(companyDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public CompanyDTO addCompany(@RequestBody CompanyDTO companyDTO) {
        return companyMapper.companyToDto(companyService.addCompany(companyMapper.dtoToCompany(companyDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> modifyCompany(@PathVariable long id, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO resultCompanyDTO = companyMapper.companyToDto(companyService.modifyCompany(id,companyMapper.dtoToCompany(companyDTO)));
        if (resultCompanyDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultCompanyDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
    }

    // region Employee adding

    @PostMapping("/{companyId}/add")
    public ResponseEntity<CompanyDTO> addEmployeeToCompany(@PathVariable long companyId, @RequestBody EmployeeDTO employeeDTO){
        CompanyDTO companyDTO = companyMapper.companyToDto(companyService.addEmployeeToCompany(companyId, employeeMapper.dtoToEmployee(employeeDTO)));
        if(companyDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(companyDTO);
    }

    @PostMapping("/{companyId}/exchange")
    public ResponseEntity<CompanyDTO> exchangeFullEmployeeList(@PathVariable long companyId, @RequestBody List<EmployeeDTO> employeeDTOList){
        CompanyDTO companyDTO = companyMapper.companyToDto(companyService.exchangeFullEmployeeList(companyId, employeeMapper.dtosToEmployees(employeeDTOList)));
        if(companyDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(companyDTO);
    }

    @DeleteMapping("/{companyId}/delete")
    public ResponseEntity<CompanyDTO> deleteEmployeeFromCompany(@PathVariable long companyId, @RequestParam(required = true) long employeeDTOId){
        CompanyDTO companyDTO = companyMapper.companyToDto(companyService.deleteEmployeeFromCompany(companyId, employeeDTOId));
        if(companyDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(companyDTO);
    }
    // endregion
}
