package hu.cubix.hr.ektora.model;

import java.util.List;

public class CompanyDTO {
    private Long id;
    private String registrationNumber;
    private String name;
    private String address;
    private List<EmployeeDTO> employeeDTOList;

    public CompanyDTO(Long id, String registrationNumber, String name, String address, List<EmployeeDTO> employeeDTOList) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employeeDTOList = employeeDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<EmployeeDTO> getEmployeeDTOList() {
        return employeeDTOList;
    }

    public void setEmployeeDTOList(List<EmployeeDTO> employeeDTOList) {
        this.employeeDTOList = employeeDTOList;
    }
}
