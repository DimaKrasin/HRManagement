package net.restapp.model;

import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    @Pattern(regexp= RegexpPatterns.patternStringWithNumbersLettersAndDash,
            message = RegexpPatterns.messageStringWithNumbersLettersAndDash)
    private String name;

    @Column(name = "salary_coef")
    private BigDecimal salary_coef;

    @OneToMany(mappedBy = "event")
    List<WorkingHours> workingHoursList;
}
