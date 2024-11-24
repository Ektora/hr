package hu.cubix.hr.ektora.service;

import hu.cubix.hr.ektora.config.HrConfigProperties;
import hu.cubix.hr.ektora.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class SmartEmployeeService implements EmployeeService{

//    @Value("${hr.employee.smart.maxYearsThreshold}")
//    private double maxYearsThreshold;
//    @Value("${hr.employee.smart.middleYearsThreshold}")
//    private double middleYearsThreshold;
//    @Value("${hr.employee.smart.minYearsThreshold}")
//    private double minYearsThreshold;
//    @Value("${hr.employee.smart.maxYearsPercent}")
//    private int maxYearsPercent;
//    @Value("${hr.employee.smart.middleYearsPercent}")
//    private int middleYearsPercent;
//    @Value("${hr.employee.smart.minYearsPercent}")
//    private int minYearsPercent;
//    @Value("${hr.employee.smart.defaultPercent}")
//    private int defaultPercent;

    @Autowired
    HrConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        Period period = Period.between(employee.getStartedWorking().toLocalDate(), LocalDate.now());
        int totalMonth = period.getYears() * 12 + period.getMonths();

        /*if(totalMonth >= maxYearsThreshold * 12)
            return maxYearsPercent;
        else if(totalMonth >= middleYearsThreshold * 12)
            return middleYearsPercent;
        else if(totalMonth >= minYearsThreshold * 12)
            return minYearsPercent;
        else
            return defaultPercent;
    }*/
        if (totalMonth >= config.getEmployee().getSmart().getMaxYearsThreshold() * 12)
            return config.getEmployee().getSmart().getMaxYearsPercent();
        else if (totalMonth >= config.getEmployee().getSmart().getMiddleYearsThreshold() * 12)
            return config.getEmployee().getSmart().getMiddleYearsPercent();
        else if (totalMonth >= config.getEmployee().getSmart().getMinYearsThreshold() * 12)
            return config.getEmployee().getSmart().getMinYearsPercent();
        else
            return config.getEmployee().getSmart().getDefaultPercent();
    }
}
