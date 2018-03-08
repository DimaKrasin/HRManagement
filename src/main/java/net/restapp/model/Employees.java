package net.restapp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import net.restapp.json.EmployeesJsonSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")

@Getter
@Setter
@JsonSerialize(using = EmployeesJsonSerializer.class)



public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name ="availableVacationDay")
    private int availableVacationDay;// available day for Vacation

    @Column(name = "experience")
    private int experience;

    @Column(name = "startWorkingDate")
    private Date startWorkingDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    private  List<ArchiveSalary> archiveSalary;

    @OneToMany(mappedBy = "employees")
    private List<WorkingHours> workingHoursList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAvailableVacationDay() {
        return availableVacationDay;
    }

    public void setAvailableVacationDay(int availableVacationDay) {
        this.availableVacationDay = availableVacationDay;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Date getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(Date startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ArchiveSalary> getArchiveSalary() {
        return archiveSalary;
    }

    public void setArchiveSalary(List<ArchiveSalary> archiveSalary) {
        this.archiveSalary = archiveSalary;
    }

    public List<WorkingHours> getWorkingHoursList() {
        return workingHoursList;
    }

    public void setWorkingHoursList(List<WorkingHours> workingHoursList) {
        this.workingHoursList = workingHoursList;
    }
}
