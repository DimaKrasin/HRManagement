package net.restapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    private String name;

    @Column(name = "salary_coef")
    private BigDecimal salary_coef;

    @OneToMany(mappedBy = "status")
    List<WorkingHours> workingHoursList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary_coef() {
        return salary_coef;
    }

    public void setSalary_coef(BigDecimal salary_coef) {
        this.salary_coef = salary_coef;
    }

    public List<WorkingHours> getWorkingHoursList() {
        return workingHoursList;
    }

    public void setWorkingHoursList(List<WorkingHours> workingHoursList) {
        this.workingHoursList = workingHoursList;
    }
}
