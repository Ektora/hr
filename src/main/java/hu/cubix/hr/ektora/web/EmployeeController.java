package hu.cubix.hr.ektora.web;

import hu.cubix.hr.ektora.mapper.EmployeeMapper;
import hu.cubix.hr.ektora.model.Employee;
import hu.cubix.hr.ektora.model.EmployeeDTO;
import hu.cubix.hr.ektora.service.AbstractEmployeeService;
import hu.cubix.hr.ektora.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {



//    @Autowired
//    EmployeeService employeeService;

    @Autowired
    AbstractEmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    public EmployeeController(){

    }

    @GetMapping
    public List<EmployeeDTO> getEmployees(@RequestParam(required = false) Integer salary){
        if(salary != null){
            return employeeMapper.employeesToDtos(employeeService.getEmployees(salary));
        }
        return new ArrayList<>(employeeMapper.employeesToDtos(employeeService.getEmployees(null)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable long id){
        //EmployeeDTO employeeDTO = employeesMap.get(id);
        EmployeeDTO employeeDTO = employeeMapper.employeeToDto(employeeService.getById(id));
        if(employeeDTO != null)
            return ResponseEntity.ok(employeeDTO);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/salary")
    public int getPayRaisePercent(@RequestBody EmployeeDTO employeeDto){
        //return employeeService.getPayRaisePercent(employee);
        return employeeService.getPayRaisePercent(employeeMapper.dtoToEmployee(employeeDto));
    }

    @PostMapping
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        //employeesMap.put(employeeDTO.getId(),employeeDTO);
        return employeeMapper
                .employeeToDto(employeeService.createEmployee(employeeMapper.dtoToEmployee(employeeDTO)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> modifyEmployee(@PathVariable long id, @Valid @RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO resultEmployeeDto = employeeMapper
                .employeeToDto(employeeService.modifyEmployee(id,employeeMapper.dtoToEmployee(employeeDTO)));
        if(resultEmployeeDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultEmployeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
    }

}
