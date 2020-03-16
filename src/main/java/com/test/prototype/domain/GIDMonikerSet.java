package com.test.prototype.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A GIDMonikerSet.
 */
@Entity
@Table(name = "gid_moniker_set")
public class GIDMonikerSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "gid_moniker_set_gidmoniker",
               joinColumns = @JoinColumn(name = "gidmoniker_set_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "gidmoniker_id", referencedColumnName = "id"))
    private Set<GIDMoniker> gIDMonikers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<GIDMoniker> getGIDMonikers() {
        return gIDMonikers;
    }

    public GIDMonikerSet gIDMonikers(Set<GIDMoniker> gIDMonikers) {
        this.gIDMonikers = gIDMonikers;
        return this;
    }

    public GIDMonikerSet addGIDMoniker(GIDMoniker gIDMoniker) {
        this.gIDMonikers.add(gIDMoniker);
        gIDMoniker.getGIDMonikerSets().add(this);
        return this;
    }

    public GIDMonikerSet removeGIDMoniker(GIDMoniker gIDMoniker) {
        this.gIDMonikers.remove(gIDMoniker);
        gIDMoniker.getGIDMonikerSets().remove(this);
        return this;
    }

    public void setGIDMonikers(Set<GIDMoniker> gIDMonikers) {
        this.gIDMonikers = gIDMonikers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GIDMonikerSet)) {
            return false;
        }
        return id != null && id.equals(((GIDMonikerSet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GIDMonikerSet{" +
            "id=" + getId() +
            "}";
    }
}
