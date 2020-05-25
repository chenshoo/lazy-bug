package lazy.database.entities;

import javax.persistence.*;

@Entity
public class Dog {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Human owner;

    public Dog() {
    }

    public Dog(String name, String color, Human owner) {
        this.name = name;
        this.color = color;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }
}
