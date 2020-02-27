package application.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @NotNull
    @Column(name = "id")
    public int id;

    @NotNull
    @Column(name="name")
    private String name;

}

