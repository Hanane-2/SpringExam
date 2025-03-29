package employe.management.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import employe.management.Modele.Department;
import employe.management.Modele.Employe;
import employe.management.Repository.EmployeRepository;
import jakarta.transaction.Transactional;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeeRepository;

    @Transactional
    public Employe addEmployee(Employe employe) {
        
        return employeeRepository.save(employe);
    }

    public List<Employe> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public boolean deleteEmployeeById(Long id) {
        // Delete employee photo
        String path = "src/main/resources/static/photos/" + id + ".png";
        File file = new File(path);
        if (file.exists()) file.delete();

        employeeRepository.deleteById(id);
        return !employeeRepository.existsById(id);
    }

    public Employe updateEmployee(Employe employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employe> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employe> getEmployeeByName(String name) {
        return employeeRepository.findByNameIgnoreCase(name);
    }

    public List<Employe> deleteEmployeesOlderThan(int age) {
        List<Employe> olderEmployees = employeeRepository.findByAgeGreaterThan(age);
        for (Employe employee : olderEmployees) {
            deleteEmployeeById(employee.getId());
        }
        return olderEmployees;
    }

    public List<Employe> getEmployeePagination(int page, int size, String field) {
        Pageable pg = PageRequest.of(page, size, Sort.by(field));
        Page<Employe> pageEmployees = employeeRepository.findAll(pg);
        return pageEmployees.getContent();
    }

    public List<Employe> getAllEmployeesByDepartment(Department department) {
        return employeeRepository.findByDepartment(department);
    }
}