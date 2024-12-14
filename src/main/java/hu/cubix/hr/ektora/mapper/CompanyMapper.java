package hu.cubix.hr.ektora.mapper;

import hu.cubix.hr.ektora.model.Company;
import hu.cubix.hr.ektora.model.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface CompanyMapper {

    List<Company> dtosToCompanies(List<CompanyDTO> companyDTOList);
    List<CompanyDTO> companiesToDtos(List<Company> companyList);

    @Mapping(source = "employeeDTOList", target = "employeeList")
    Company dtoToCompany(CompanyDTO companyDTO);
    @Mapping(source = "employeeList", target = "employeeDTOList")
    CompanyDTO companyToDto(Company company);

}
