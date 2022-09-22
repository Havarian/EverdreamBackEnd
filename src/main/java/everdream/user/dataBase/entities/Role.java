package everdream.user.dataBase.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table (name = "roles")
public class Role {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column (name = "name")
    @Enumerated (EnumType.STRING)
    private ERole name;
    @OneToMany (mappedBy = "role", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UserRole> userRoles;

    public Role(ERole name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null) return false;
        return name == role.name;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
