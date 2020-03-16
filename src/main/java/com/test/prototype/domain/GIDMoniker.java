package com.test.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.test.prototype.domain.enumeration.GIDMonikerPrefix;

/**
 * A GIDMoniker.
 */
@Entity
@Table(name = "gid_moniker")
public class GIDMoniker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moniker")
    private String moniker;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefix")
    private GIDMonikerPrefix prefix;

    @ManyToMany(mappedBy = "gIDMonikers")
    @JsonIgnore
    private Set<GIDMonikerSet> gIDMonikerSets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoniker() {
        return moniker;
    }

    public GIDMoniker moniker(String moniker) {
        this.moniker = moniker;
        return this;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public GIDMonikerPrefix getPrefix() {
        return prefix;
    }

    public GIDMoniker prefix(GIDMonikerPrefix prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(GIDMonikerPrefix prefix) {
        this.prefix = prefix;
    }

    public Set<GIDMonikerSet> getGIDMonikerSets() {
        return gIDMonikerSets;
    }

    public GIDMoniker gIDMonikerSets(Set<GIDMonikerSet> gIDMonikerSets) {
        this.gIDMonikerSets = gIDMonikerSets;
        return this;
    }

    public GIDMoniker addGIDMonikerSet(GIDMonikerSet gIDMonikerSet) {
        this.gIDMonikerSets.add(gIDMonikerSet);
        gIDMonikerSet.getGIDMonikers().add(this);
        return this;
    }

    public GIDMoniker removeGIDMonikerSet(GIDMonikerSet gIDMonikerSet) {
        this.gIDMonikerSets.remove(gIDMonikerSet);
        gIDMonikerSet.getGIDMonikers().remove(this);
        return this;
    }

    public void setGIDMonikerSets(Set<GIDMonikerSet> gIDMonikerSets) {
        this.gIDMonikerSets = gIDMonikerSets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GIDMoniker)) {
            return false;
        }
        return id != null && id.equals(((GIDMoniker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GIDMoniker{" +
            "id=" + getId() +
            ", moniker='" + getMoniker() + "'" +
            ", prefix='" + getPrefix() + "'" +
            "}";
    }
}
