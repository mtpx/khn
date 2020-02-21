package application.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @NotEmpty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @NotEmpty
    @Column(name="name")
    private String name;

}

