package com.happylife.library.myspringbootproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = User.NamedQuery_GET_SUBSCRIBERS_OF_PERIODICAL_ID,
                procedureName = "GET_SUBSCRIBERS_OF_PERIODICAL_ID",
                resultClasses = User.class,
                parameters = {
                        @StoredProcedureParameter(type = Long.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(type = void.class, mode = ParameterMode.REF_CURSOR),
                }
        ),
})

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    public static final String NamedQuery_GetEmployees = "getEmployees";
    public static final String NamedQuery_GET_SUBSCRIBERS_OF_PERIODICAL_ID
            = "getSubscribersOfPeriodicalId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @Size(max = 3)
    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "zip_code")
    private int zipCode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Orders> orders;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
