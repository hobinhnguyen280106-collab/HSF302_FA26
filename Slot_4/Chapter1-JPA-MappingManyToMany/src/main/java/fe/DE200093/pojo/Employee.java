package fe.DE200093.pojo;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(precision = 18, scale = 2)
    private BigDecimal salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "hehehehehehehe",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    public Employee() {}

    public Employee(String email, String fullName, Gender gender, BigDecimal salary, LocalDate hireDate) {
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.salary = salary;
        this.hireDate = hireDate;
        this.active = true;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getFullName() {

        return fullName;
    }
    public void setFullName(String fullName) {

        this.fullName = fullName;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {

        this.salary = salary;
    }
    public LocalDate getHireDate() {

        return hireDate;
    }
    public void setHireDate(LocalDate hireDate) {

        this.hireDate = hireDate;
    }
    public String getEmail() {

        return email;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {

        this.gender = gender;
    }
    public boolean isActive() {

        return active;
    }
    public void setActive(boolean active) {

        this.active = active;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    @Override
    public boolean equals(Object o) {
        // Lý do không dùng id: Id chỉ được sinh ra sau khi entity được lưu vào DB (persist).
        // Trước khi lưu, id là null. Dùng Business Key (email - unique & not null) giúp
        // Set nhận diện đúng entity ngay cả khi entity chưa được persist.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public void assignToProject(Project p) {
        if (p != null) {
            this.projects.add(p);
            p.getEmployees().add(this);
        }
    }

    public void unassignFromProject(Project p) {
        this.projects.remove(p);
        p.getEmployees().remove(this);
    }
}
