package hu.cubix.hr.ektora.model;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "A név nem lehet üres és nem állhat csak szóközökből!")
    private String name;

    @NotBlank(message = "A beosztás nem lehet üres és nem állhat csak szóközökből!")
    private String job;

    @Positive(message = "A fizetésnek pozitív értéknek kell lennie!")
    private int salary;

    @Past(message = "A belépés dátumának múltbéli időpontnak kell lennie!")
    private LocalDateTime startedWorking;

    public Long getId() {
        return id;
    }

    public EmployeeDTO(Long id, String name, String job, int salary, LocalDateTime startedWorking) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.startedWorking = startedWorking;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getStartedWorking() {
        return startedWorking;
    }

    public void setStartedWorking(LocalDateTime startedWorking) {
        this.startedWorking = startedWorking;
    }
}
