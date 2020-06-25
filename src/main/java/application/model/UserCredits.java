package application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = UserCredits.GET_BY_HOUSE_ID, query = UserCredits.QUERY_GET_BY_HOUSE_ID),
        @NamedQuery(name = UserCredits.GET_BY_FLAT_ID, query = UserCredits.QUERY_GET_BY_FLAT_ID),
        @NamedQuery(name = UserCredits.GET_BY_PLOT_ID, query = UserCredits.QUERY_GET_BY_PLOT_ID)
        })
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userCredits")
public class UserCredits {

    public static final String GET_BY_HOUSE_ID = "UserRealAssets.get_by_houseId";
    public static final String QUERY_GET_BY_HOUSE_ID = "select u from UserRealAssets u where u.house.id= :houseId";

    public static final String GET_BY_FLAT_ID = "UserRealAssets.get_by_flatId";
    public static final String QUERY_GET_BY_FLAT_ID = "select u from UserRealAssets u where u.flat.id= :flatId";

    public static final String GET_BY_PLOT_ID = "UserRealAssets.get_by_plotId";
    public static final String QUERY_GET_BY_PLOT_ID = "select u from UserRealAssets u where u.plot.id= :plotId";

    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "credit")
    private Credit credit;

    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Column(name="amoutOfInstallment",nullable = false)
    private Integer amountOfInstallment;
}

