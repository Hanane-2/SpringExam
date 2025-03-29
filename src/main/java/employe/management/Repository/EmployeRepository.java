package employe.management.Repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import employe.management.Modele.Department;
import employe.management.Modele.Employe;


@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByNameIgnoreCase(String name);
    List<Employe> findByAgeGreaterThan(int age);
    List<Employe> findByDepartment(Department department);
}