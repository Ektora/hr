package hu.cubix.hr.ektora.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="hr")
@Component
public class HrConfigProperties {

    private Employee employee = new Employee();

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static class Employee{
        private Smart smart = new Smart();

        public Smart getSmart() {
            return smart;
        }

        public void setSmart(Smart smart) {
            this.smart = smart;
        }
    }

    public static class Smart{
        private double maxYearsThreshold;
        private double middleYearsThreshold;
        private double minYearsThreshold;
        private int maxYearsPercent;
        private int middleYearsPercent;
        private int minYearsPercent;
        private int defaultPercent;

        public int getDefaultPercent() {
            return defaultPercent;
        }

        public void setDefaultPercent(int defaultPercent) {
            this.defaultPercent = defaultPercent;
        }

        public int getMinYearsPercent() {
            return minYearsPercent;
        }

        public void setMinYearsPercent(int minYearsPercent) {
            this.minYearsPercent = minYearsPercent;
        }

        public int getMiddleYearsPercent() {
            return middleYearsPercent;
        }

        public void setMiddleYearsPercent(int middleYearsPercent) {
            this.middleYearsPercent = middleYearsPercent;
        }

        public int getMaxYearsPercent() {
            return maxYearsPercent;
        }

        public void setMaxYearsPercent(int maxYearsPercent) {
            this.maxYearsPercent = maxYearsPercent;
        }

        public double getMinYearsThreshold() {
            return minYearsThreshold;
        }

        public void setMinYearsThreshold(double minYearsThreshold) {
            this.minYearsThreshold = minYearsThreshold;
        }

        public double getMiddleYearsThreshold() {
            return middleYearsThreshold;
        }

        public void setMiddleYearsThreshold(double middleYearsThreshold) {
            this.middleYearsThreshold = middleYearsThreshold;
        }

        public double getMaxYearsThreshold() {
            return maxYearsThreshold;
        }

        public void setMaxYearsThreshold(double maxYearsThreshold) {
            this.maxYearsThreshold = maxYearsThreshold;
        }
    }



}
