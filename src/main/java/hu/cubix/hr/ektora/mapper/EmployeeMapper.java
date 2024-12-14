package hu.cubix.hr.ektora.mapper;

import hu.cubix.hr.ektora.model.Employee;
import hu.cubix.hr.ektora.model.EmployeeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

     List<Employee> dtosToEmployees(List<EmployeeDTO> employeeDTOList);
     List<EmployeeDTO> employeesToDtos(List<Employee> employeeList);

    EmployeeDTO employeeToDto(Employee employee);
     Employee dtoToEmployee(EmployeeDTO employeeDTO);
}
