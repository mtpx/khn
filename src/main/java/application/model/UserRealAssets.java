package application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
@NamedQueries({
        @NamedQuery(name = UserRealAssets.GET_BY_HOUSE_ID, query = UserRealAssets.QUERY_GET_BY_HOUSEID)
})
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userRealAssets")
public class UserRealAssets {

    public static final String GET_BY_HOUSE_ID = "UserRealAssets.get_by_houseId";
    public static final String QUERY_GET_BY_HOUSEID = "select u from UserRealAssets u where u.house.id= :houseId";

    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "houseId")
    private House house;

    @ManyToOne
    @JoinColumn(name = "flatId")
    private Flat flat;

    @ManyToOne
    @JoinColumn(name = "plotId")
    private Plot plot;
}

