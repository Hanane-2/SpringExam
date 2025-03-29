package employe.management.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import employe.management.Modele.Department;
import employe.management.Modele.Employe;
import employe.management.Repository.DepartmentRepository;
import employe.management.Service.EmployeService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EmployeController {

    @Autowired private EmployeService employeeService;
    @Autowired private DepartmentRepository departmentRepository;

    @PostMapping("/employees/{departmentId}")
    public Employe addEmployee(
            @PathVariable Long departmentId,  // Utilisation de @PathVariable au lieu de @RequestParam
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam MultipartFile photo
    ) throws IllegalStateException, IOException {

        String path = "src/main/resources/static/photos/" + (id != null ? id : System.currentTimeMillis()) + ".png";
        photo.transferTo(Path.of(path));

        String url = "/api/photos/" + (id != null ? id : System.currentTimeMillis());
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employe employee = new Employe(id, name, age, url, department);
        return employeeService.addEmployee(employee);
    }


    @GetMapping("/photos/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        String path = "src/main/resources/static/photos/" + id + ".png";
        FileSystemResource file = new FileSystemResource(path);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(file);
    }

    @GetMapping("/employees")
    public List<Employe> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/department/{id}/employees")
    public List<Employe> getAllEmployeesByDepartment(@PathVariable Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        return employeeService.getAllEmployeesByDepartment(department);
    }

    @DeleteMapping("/employees/{id}")
    public boolean deleteEmployeeById(@PathVariable Long id) {
        return employeeService.deleteEmployeeById(id);
    }

    @DeleteMapping("/employees/age/{age}")
    public List<Employe> deleteEmployeesOlderThan(@PathVariable int age) {
        return employeeService.deleteEmployeesOlderThan(age);
    }

    @PutMapping("/employees/{id}")
    public Employe updateEmployee(
            @PathVariable Long id,
            @RequestBody Employe employee) {
        employee.setId(id);
        return employeeService.updateEmployee(employee);
    }

    @GetMapping("/employees/{id}")
    public Optional<Employe> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
}