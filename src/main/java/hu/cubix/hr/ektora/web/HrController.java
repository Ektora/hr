package hu.cubix.hr.ektora.web;

import hu.cubix.hr.ektora.model.EmployeeDTO;
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
public class HrController {

    private Map<Long,EmployeeDTO> employeesMap;

    public HrController(){
        employeesMap = new HashMap<>();
        employeesMap.put(1L,new EmployeeDTO((long) 1, "Zsolt", "IT szakértő", 600_000, LocalDateTime.of(2017, 1, 1, 0, 0)));
        employeesMap.put(2L,new EmployeeDTO((long) 2, "Tibor", "IT csoportvezető", 550_000, LocalDateTime.of(2022, 3, 1, 0, 0)));
        employeesMap.put(3L,new EmployeeDTO((long) 3, "Kristóf", "IT technikus", 450_000, LocalDateTime.of(2023, 9, 1, 0, 0)));
        employeesMap.put(4L,new EmployeeDTO((long) 4, "Krisztián", "IT vezető", 900_000, LocalDateTime.of(2003, 5, 1, 0, 0)));

    }

    @GetMapping
    public List<EmployeeDTO> getEmployees(@RequestParam(required = false) Integer salary){
        if(salary != null){
            return employeesMap.values().stream()
                    .filter(employeeDTO -> employeeDTO.getSalary() >= salary)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(employeesMap.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable long id){
        EmployeeDTO employeeDTO = employeesMap.get(id);
        if(employeeDTO != null)
            return ResponseEntity.ok(employeeDTO);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO){
        employeesMap.put(employeeDTO.getId(),employeeDTO);
        return employeeDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> modifyEmployee(@PathVariable long id,@RequestBody EmployeeDTO employeeDTO){
        if(!employeesMap.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        employeeDTO.setId(id);
        employeesMap.put(id,employeeDTO);
        return ResponseEntity.ok(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id){
        employeesMap.remove(id);
    }

}
