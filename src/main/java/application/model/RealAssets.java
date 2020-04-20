package application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "realAssets")
public class RealAssets {

    @Id
    @NotNull
    @Column(name = "id")
    public int id;

    @Column
    @NotNull
    public String type;

}

