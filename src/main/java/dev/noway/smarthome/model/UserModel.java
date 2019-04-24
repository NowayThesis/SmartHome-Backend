package dev.noway.smarthome.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "smart_home")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    private String email;

    private String password;

    @Transient
    @Column(name = "password_2")
    private String password_2;

    private int role;

    public UserModel() {
    }

    public UserModel(String password, String email, int role) {
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
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
