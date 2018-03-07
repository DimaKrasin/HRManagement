package net.restapp.restcontroller;

import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/workingHours")
public class WorkingHoursController {


    @Autowired
    WorkingHoursService workingHoursService;

    @Autowired
    EmployeesService employeesService;

    MyResponseRequest myResponseRequest = new MyResponseRequest(new WorkingHours());

    // Принамает дату создания события
    // Возвращяет List<Employees> со всеми свободными сотрудника на данную дату
    @RequestMapping(value = "/getAvailableByDate/{date}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Employees>> getAvailableEmployees(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        if (date == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Employees> allEmployeesList = employeesService.getAll();
        List<Employees> notAvailableEmployeesList = workingHoursService.findAllEmployeeForDate(date);
        List<Employees> availableEmployees = new ArrayList<>();

        for (int i = 0; i < allEmployeesList.size(); i++) {
            for (int j = 0; j < notAvailableEmployeesList.size(); j++) {

                if (allEmployeesList.get(i) != notAvailableEmployeesList.get(j)) {
                    availableEmployees.add(allEmployeesList.get(i));
                }
            }
        }

        return new ResponseEntity<>(availableEmployees, HttpStatus.OK);
    }


    //GET BY ID
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getWorkingHours(@PathVariable("workingHoursId") Long workingHoursId,
                                                HttpServletRequest request){

        if (workingHoursId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "working Hours Id must be not null");
        }

        WorkingHours workingHours = workingHoursService.getById(workingHoursId);

        if (workingHours== null) {
            return myResponseRequest.notFoundRequest(request,workingHoursId);
        }
        return new ResponseEntity<>(workingHours, HttpStatus.OK);
    }

    //Delete
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteWorkingHoursI(@PathVariable("workingHoursId") Long workingHoursId,
                                                   HttpServletRequest request){

        if (workingHoursId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "working Hours Id must be not null");
        }
        WorkingHours workingHours = workingHoursService.getById(workingHoursId);

        if (workingHours == null) {
            return myResponseRequest.notFoundRequest(request,workingHoursId);
        }
        workingHoursService.delete(workingHoursId);
        if (workingHoursService.getById(workingHoursId) != null){
            return myResponseRequest.bedRequest(
                    request,
                    "can't delete.");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //EDIT
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editWorkingHours(@PathVariable("workingHoursId") Long workingHoursId,
                                                 @RequestBody @Valid WorkingHours workingHours,
                                                 HttpServletRequest request){

        if (workingHoursId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "working Hours Id must be not null");
        }

        WorkingHours workingHours2 = workingHoursService.getById(workingHoursId);

        if (workingHours2 == null) {
            return myResponseRequest.notFoundRequest(request,workingHoursId);
        }
        workingHours.setId(workingHoursId);
        workingHoursService.save(workingHours);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //GET ALL
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllWorkingHours(HttpServletRequest request){
        List<WorkingHours> workingHours = workingHoursService.getAll();
        if (workingHours.isEmpty()) {
            return myResponseRequest.notFoundRequest(request,null);
        }
        return new ResponseEntity<>(workingHours,HttpStatus.OK);
    }


    //ADD
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveWorkingHours(@RequestBody @Valid WorkingHours workingHours,
                                                 UriComponentsBuilder builder,
                                                 HttpServletRequest request){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (workingHours == null){
            return myResponseRequest.bedRequest(
                    request,
                    "Working Hours Id be not null");
        }
        workingHoursService.save(workingHours);

        httpHeaders.setLocation(builder.path("/workingHours/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(workingHours,httpHeaders,HttpStatus.CREATED);
    }

}
