package hu.cubix.hr.ektora.config;

import hu.cubix.hr.ektora.service.DefaultEmployeeService;
import hu.cubix.hr.ektora.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class EmployeeConfiguration {

    @Bean
    public EmployeeService employeeService(){
        return new DefaultEmployeeService();
    }

}