package modelo;

import javax.persistence.*;

@Entity
@Table(name = "helbidea")
public class Helbidea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // requiere el ALTER TABLE anterior

    @Column(name = "helbidea", nullable = false, length = 100)
    private String helbidea;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "nan", nullable = false) // FK a ikaslea(nan)
    private Ikaslea ikaslea;

    public Helbidea() {}
    public Helbidea(String helbidea) { this.helbidea = helbidea; }

    // getters/setters
    public Long getId() { return id; }
    public String getHelbidea() { return helbidea; }
    public void setHelbidea(String helbidea) { this.helbidea = helbidea; }
    public Ikaslea getIkaslea() { return ikaslea; }
    public void setIkaslea(Ikaslea ikaslea) { this.ikaslea = ikaslea; }
}
