package pl.KielbasaShop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(of = "id")
public class Order {

    @Getter
    @Id
    UUID id = UUID.randomUUID();

    @Getter
    @ManyToMany(cascade = {CascadeType.MERGE})
    List<Kielbasa> kielbasas = new ArrayList<>();

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    Date creationDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = new Date();
    }
}
