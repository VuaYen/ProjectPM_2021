package miu.edu.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String gender;
    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    @NotNull
    @Column(unique = true)
    private String userName;


    @Embedded
    private Address address;

    private boolean status;
    private LocalDateTime createdDate;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Set<Role> roles = new HashSet<>();

    @Size(min = 6, message = "{error.password.size}")
    private String password;

    @Transient
    private String confirmPassword;

    private UserType userType;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
