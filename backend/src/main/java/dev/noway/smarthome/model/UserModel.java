package dev.noway.smarthome.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "smart_home")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Transient
    @Column(name = "password_2")
    private String password_2;

    @Column(name = "role")
    private int role;

    public UserModel() {
    }

    public UserModel(String password, String email, int role) {
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_2() {
        return password_2;
    }

    public void setPassword_2(String password_2) {
        this.password_2 = password_2;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel user = (UserModel) o;
        return id == user.id &&
                role == user.role &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(password_2, user.password_2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  email, password, password_2,role);
    }
}
