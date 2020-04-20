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
@Table(name = "userRealAssets")
public class UserRealAssets {

    @Id
    @NotNull
    @Column(name = "id")
    public int id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "houseId", nullable = false)
    private House house;

    @ManyToOne
    @JoinColumn(name = "flatId", nullable = false)
    private Flat flat;

    @ManyToOne
    @JoinColumn(name = "plotId", nullable = false)
    private Plot plot;

}

