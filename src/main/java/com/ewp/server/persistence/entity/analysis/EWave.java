package com.ewp.server.persistence.entity.analysis;

import com.ewp.server.persistence.entity.analysis.Candlestick;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "ewave")
public class EWave {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ewave_id_seq")
    @SequenceGenerator(name = "ewave_id_seq", sequenceName = "ewave_id_seq", allocationSize = 1)
    private long id;
    
    @Basic
    private int ptypeValue;
    @Transient
    private PType ptype;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ewave_sticks", 
        joinColumns = @JoinColumn(name = "ewave_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "candlestick_id", referencedColumnName = "id"))
    private Collection<Candlestick> ewaveSticks;

    @ManyToMany()
    @JoinTable(name = "decomposite_ewave", 
        joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"))
    private Collection<EWave> decompositeEWave;

    //@ManyToMany(mappedBy = "decompositeEWave")  - do not support auto-remove
    @ManyToMany()
    @JoinTable(name = "decomposite_ewave", 
        joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"))
    private Collection<EWave> headers; 


    public EWave() {
        super();
    }

    @PostLoad
    void fillTransient() {
        if (ptypeValue > 0) 
            this.ptype = PType.of(ptypeValue);
    }
    @PrePersist
    @PreUpdate
    void fillPersistent() {
        if (this.ptype != null) 
            this.ptypeValue = this.ptype.getPType();
    }

    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }

    public Collection<Candlestick> getEwaveSticks() {
        return ewaveSticks;
    }
    public void setEwaveSticks(final Collection<Candlestick> ewaveSticks) {
        this.ewaveSticks = ewaveSticks;
    }

    public Collection<EWave> getDecompositeEWave() {
        return decompositeEWave;
    }
    public void setDecompositeEWave(final Collection<EWave> decompositeEWave) {
        this.decompositeEWave = decompositeEWave;
    }

    public PType getPType() {
        return ptype;
    }
    public void setPType(PType ptype) {
        this.ptype = ptype;
    }

    public Collection<EWave> getHeaders() {
        return headers;
    }
    public void setHeaders(Collection<EWave> headers) {
        this.headers = headers;
    }
    /*public void setPTypeValue(int ptypeValue) {
        this.ptypeValue = ptypeValue;
    }*/
}
