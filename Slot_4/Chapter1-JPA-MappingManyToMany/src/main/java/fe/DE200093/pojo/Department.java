package fe.DE200093.pojo;

import fe.DE200093.util.JPAUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String location;

    public Department() {}

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Employee> employees = new java.util.ArrayList<>();

    public java.util.List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(java.util.List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee e) {
        this.employees.add(e);
        e.setDepartment(this);
    }

    public void removeEmployee(Employee e) {
        this.employees.remove(e);
        e.setDepartment(null);
    }


}
