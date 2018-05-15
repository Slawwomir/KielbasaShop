package pl.KielbasaShop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.UUID;

@Entity
@EqualsAndHashCode(of = "id")
@NamedQueries(value = {
        @NamedQuery(name = Kielbasa.FIND_ALL, query = "SELECT k FROM Kielbasa k")
})
public class Kielbasa {
    public static final String FIND_ALL = "Kielbasa.FIND_ALL";

    @Getter
    @Id
    UUID id = UUID.randomUUID();

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    Double price;

    @Getter
    @Setter
    Integer amount;

}
