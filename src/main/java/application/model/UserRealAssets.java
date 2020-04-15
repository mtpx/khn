package application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userRealAssets")
public class UserRealAssets {

    @Id
    @NotNull
    @Column(name = "id")
    public int id;

    @Column
    @NotNull
    public int idUser;

    @Column
    @NotNull
    public int idHouse;

    @Column
    @NotNull
    public int idFlat;

    @Column
    @NotNull
    public int idPlot;

}

