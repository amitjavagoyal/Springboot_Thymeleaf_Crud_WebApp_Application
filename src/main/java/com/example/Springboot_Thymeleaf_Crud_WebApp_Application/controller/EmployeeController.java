package com.example.Springboot_Thymeleaf_Crud_WebApp_Application.controller;

import com.example.Springboot_Thymeleaf_Crud_WebApp_Application.model.Employee;
import com.example.Springboot_Thymeleaf_Crud_WebApp_Application.service.EmployeeService;
import com.example.Springboot_Thymeleaf_Crud_WebApp_Application.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;

    //Display list of employees
    @GetMapping("/")
    public String viewHomePage(Model model)
    {
        //model.addAttribute("listEmployees",employeeService.getAllEmployees());
       // return "index";
        return findPaginated(1,"firstName","asc", model);
    }

      @GetMapping("/showNewEmployeeForm")
      public String showNewEmployeeForm(Model model)
      {   //create model attribute to bind form data
          Employee employee = new Employee();
          //model.addAttribute("employee", employee)
          model.addAttribute("employee",employee);
          return "new_Employee";
      }

      @PostMapping("/saveEmployee")
      public String saveEmployee(@ModelAttribute("employee") Employee employee)
      {   //save Employee top database
          employeeService.saveEmployee(employee);
          return "redirect:/";
      }

      @GetMapping("/showFormForUpdate/{id}")
      public String showFormForUpdate(@PathVariable Long id, Model model){
        // get employee from the service
          Employee employee = employeeService.getEmployeeById(id);
          //set employee as a model attribute to pre-populate the form
          model.addAttribute("employee" ,employee);
          return "update_employee";
      }

        @GetMapping("deleteEmployee/{id}")
        public String deleteEmployee(@PathVariable Long id){
        //call delete employee method
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/";
        }

        @GetMapping("/page/{pageNo}")
        public String findPaginated(@PathVariable int pageNo, @RequestParam String sortField,
                                    @RequestParam String sortDir, Model model){
            int pageSize = 5;
            Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);

            List<Employee> listEmployees = page.getContent();

            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems",page.getTotalElements());

            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");


            model.addAttribute("listEmployees",listEmployees);
            return "index";
        }

}
