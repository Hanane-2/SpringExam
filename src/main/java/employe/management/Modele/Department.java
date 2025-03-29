package employe.management.Modele;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Employe> employees;

    // No-argument constructor
    public Department() {}

    // All-argument constructor
    public Department(Long id, String name, List<Employe> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employe> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employe> employees) {
        this.employees = employees;
    }
}