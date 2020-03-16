package com.test.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A GIDIdentity.
 */
@Entity
@Table(name = "gid_identity")
public class GIDIdentity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gid")
    private String gid;

    @Column(name = "pgid")
    private String pgid;

    @OneToMany(mappedBy = "identity")
    private Set<GIDMembership> gIDMemberships = new HashSet<>();

    @OneToMany(mappedBy = "gIDIdentity")
    private Set<GIDMoniker> standardMonikerSets = new HashSet<>();

    @OneToMany(mappedBy = "gIDIdentity")
    private Set<GIDMoniker> fullMonikerSets = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<GIDMoniker> gIDMonikers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("gIDIdentities")
    private GIDUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public GIDIdentity gid(String gid) {
        this.gid = gid;
        return this;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getPgid() {
        return pgid;
    }

    public GIDIdentity pgid(String pgid) {
        this.pgid = pgid;
        return this;
    }

    public void setPgid(String pgid) {
        this.pgid = pgid;
    }

    public Set<GIDMembership> getGIDMemberships() {
        return gIDMemberships;
    }

    public GIDIdentity gIDMemberships(Set<GIDMembership> gIDMemberships) {
        this.gIDMemberships = gIDMemberships;
        return this;
    }

    public GIDIdentity addGIDMembership(GIDMembership gIDMembership) {
        this.gIDMemberships.add(gIDMembership);
        gIDMembership.setIdentity(this);
        return this;
    }

    public GIDIdentity removeGIDMembership(GIDMembership gIDMembership) {
        this.gIDMemberships.remove(gIDMembership);
        gIDMembership.setIdentity(null);
        return this;
    }

    public void setGIDMemberships(Set<GIDMembership> gIDMemberships) {
        this.gIDMemberships = gIDMemberships;
    }

    public Set<GIDMoniker> getStandardMonikerSets() {
        return standardMonikerSets;
    }

    public GIDIdentity standardMonikerSets(Set<GIDMoniker> gIDMonikers) {
        this.standardMonikerSets = gIDMonikers;
        return this;
    }

    public GIDIdentity addStandardMonikerSet(GIDMoniker gIDMoniker) {
        this.standardMonikerSets.add(gIDMoniker);
        gIDMoniker.setGIDIdentity(this);
        return this;
    }

    public GIDIdentity removeStandardMonikerSet(GIDMoniker gIDMoniker) {
        this.standardMonikerSets.remove(gIDMoniker);
        gIDMoniker.setGIDIdentity(null);
        return this;
    }

    public void setStandardMonikerSets(Set<GIDMoniker> gIDMonikers) {
        this.standardMonikerSets = gIDMonikers;
    }

    public Set<GIDMoniker> getFullMonikerSets() {
        return fullMonikerSets;
    }

    public GIDIdentity fullMonikerSets(Set<GIDMoniker> gIDMonikers) {
        this.fullMonikerSets = gIDMonikers;
        return this;
    }

    public GIDIdentity addFullMonikerSet(GIDMoniker gIDMoniker) {
        this.fullMonikerSets.add(gIDMoniker);
        gIDMoniker.setGIDIdentity(this);
        return this;
    }

    public GIDIdentity removeFullMonikerSet(GIDMoniker gIDMoniker) {
        this.fullMonikerSets.remove(gIDMoniker);
        gIDMoniker.setGIDIdentity(null);
        return this;
    }

    public void setFullMonikerSets(Set<GIDMoniker> gIDMonikers) {
        this.fullMonikerSets = gIDMonikers;
    }

    public Set<GIDMoniker> getGIDMonikers() {
        return gIDMonikers;
    }

    public GIDIdentity gIDMonikers(Set<GIDMoniker> gIDMonikers) {
        this.gIDMonikers = gIDMonikers;
        return this;
    }

    public GIDIdentity addGIDMoniker(GIDMoniker gIDMoniker) {
        this.gIDMonikers.add(gIDMoniker);
        gIDMoniker.setUser(this);
        return this;
    }

    public GIDIdentity removeGIDMoniker(GIDMoniker gIDMoniker) {
        this.gIDMonikers.remove(gIDMoniker);
        gIDMoniker.setUser(null);
        return this;
    }

    public void setGIDMonikers(Set<GIDMoniker> gIDMonikers) {
        this.gIDMonikers = gIDMonikers;
    }

    public GIDUser getUser() {
        return user;
    }

    public GIDIdentity user(GIDUser gIDUser) {
        this.user = gIDUser;
        return this;
    }

    public void setUser(GIDUser gIDUser) {
        this.user = gIDUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GIDIdentity)) {
            return false;
        }
        return id != null && id.equals(((GIDIdentity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GIDIdentity{" +
            "id=" + getId() +
            ", gid='" + getGid() + "'" +
            ", pgid='" + getPgid() + "'" +
            "}";
    }
}