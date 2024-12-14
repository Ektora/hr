package hu.cubix.hr.ektora.web;

import hu.cubix.hr.ektora.model.EmployeeDTO;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIT {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    WebTestClient webTestClient;



    @Test
    void testThatCreatedEmployeeIsListed(){
        List<EmployeeDTO> employeesBefore = gelAllEmployees();

        EmployeeDTO employeeDTO = new EmployeeDTO(5L,"Zoltán","IT technikus",350_000, LocalDateTime.of(2021, 6, 1, 0, 0));
        createEmployee(employeeDTO);

        List<EmployeeDTO> employeesAfter = gelAllEmployees();
        assertThat(employeesAfter.subList(0,employeesBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);

        assertThat(employeesAfter.get(employeesAfter.size()-1))
                .usingRecursiveComparison()
                .isEqualTo(employeeDTO);

    }

    @ParameterizedTest
    @MethodSource("invalidEmployeeProvider")
    void testThatCreateEmployeeWithInvalidInput(EmployeeDTO employeeDTO){
        List<EmployeeDTO> employeesBefore = gelAllEmployees();
        createEmployeeWithInvalidInput(employeeDTO);

        List<EmployeeDTO> employeesAfter = gelAllEmployees();
        assertThat(employeesAfter).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);
    }



    public static Stream<Arguments> invalidEmployeeProvider() {
        return Stream.of(
                Arguments.of(new EmployeeDTO(5L, "", "IT technikus", 350_000, LocalDateTime.of(2021, 6, 1, 0, 0))),
                Arguments.of(new EmployeeDTO(5L, "Zoltán", null, 350_000, LocalDateTime.of(2021, 6, 1, 0, 0))),
                Arguments.of(new EmployeeDTO(5L, "Zoltán", "IT technikus", -350_000, LocalDateTime.of(2021, 6, 1, 0, 0))),
                Arguments.of(new EmployeeDTO(5L, "Zoltán", "IT technikus", 350_000, LocalDateTime.of(2027, 6, 1, 0, 0)))
        );
    }

    @Test
    void testThatModifiedEmployeeIsChanged(){
        EmployeeDTO beforeEmployee = gelAllEmployees().get(0);
        EmployeeDTO newEmployeeDto = new EmployeeDTO(1L,"Zoltán","IT technikus",350_000, LocalDateTime.of(2021, 6, 1, 0, 0));
        EmployeeDTO resultEmployeeDto = modifyEmployee(beforeEmployee.getId(),newEmployeeDto);

        assertThat(resultEmployeeDto).usingRecursiveComparison()
                .isNotEqualTo(beforeEmployee);

        assertThat(resultEmployeeDto).usingRecursiveComparison()
                .isEqualTo(newEmployeeDto);
    }

    @Test
    void testThatModifyEmployeeWithNotExistingID(){
        List<EmployeeDTO> employeesBefore = gelAllEmployees();

        EmployeeDTO employeeDTO = new EmployeeDTO(5L,"Zoltán","IT technikus",350_000, LocalDateTime.of(2021, 6, 1, 0, 0));

        EmployeeDTO newEmployeeDto = new EmployeeDTO(6L,"Zoltán","IT technikus",350_000, LocalDateTime.of(2021, 6, 1, 0, 0));
        modifyEmployeeWithNotFound(newEmployeeDto.getId(), newEmployeeDto);

        List<EmployeeDTO> employeesAfter = gelAllEmployees();
        assertThat(employeesAfter)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);
    }

    @ParameterizedTest
    @MethodSource("invalidEmployeeProvider")
    void testThatModifyEmployeeWithInvalidInput(EmployeeDTO employeeDTO){
        List<EmployeeDTO> employeesBefore = gelAllEmployees();
        modifyEmployeeWithInvalidInput(1L,employeeDTO);

        List<EmployeeDTO> employeesAfter = gelAllEmployees();
        assertThat(employeesAfter).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);
    }


    void createEmployee(EmployeeDTO newEmployeeDTO) {
        webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(newEmployeeDTO)
                .exchange()
                .expectStatus()
                .isOk();
    }

    void createEmployeeWithInvalidInput(EmployeeDTO employeeDTO){
        webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    EmployeeDTO modifyEmployee(long id,EmployeeDTO employeeDTO) {
        EmployeeDTO resultEmployee = webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder.path(BASE_URI + "/{id}").build(id))
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDTO.class)
                .returnResult()
                .getResponseBody();
        return resultEmployee;
    }

    void modifyEmployeeWithNotFound(long id,EmployeeDTO employeeDTO) {
        webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder.path(BASE_URI + "/{id}").build(id))
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    void modifyEmployeeWithInvalidInput(long id,EmployeeDTO employeeDTO){
        webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder.path(BASE_URI + "/{id}").build(id))
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    List<EmployeeDTO> gelAllEmployees(){
        List<EmployeeDTO> employeeDTOList = webTestClient
                .get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(EmployeeDTO.class)
                .returnResult()
                .getResponseBody();

        Collections.sort(employeeDTOList, (a,b) -> Long.compare(a.getId(),b.getId()));
        return employeeDTOList;
    }
}