package com.test.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToOne
    @JoinColumn(unique = true)
    private GIDMonikerSet monikers;

    @OneToOne
    @JoinColumn(unique = true)
    private GIDMonikerSet fullMonikerSet;

    @OneToOne
    @JoinColumn(unique = true)
    private GIDMonikerSet standardMonikerSet;

    @ManyToMany
    @JoinTable(name = "gid_identity_memberships",
               joinColumns = @JoinColumn(name = "gididentity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "memberships_id", referencedColumnName = "id"))
    private Set<GIDMembership> memberships = new HashSet<>();

    @ManyToMany(mappedBy = "identities")
    @JsonIgnore
    private Set<GIDUser> users = new HashSet<>();

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

    public GIDMonikerSet getMonikers() {
        return monikers;
    }

    public GIDIdentity monikers(GIDMonikerSet gIDMonikerSet) {
        this.monikers = gIDMonikerSet;
        return this;
    }

    public void setMonikers(GIDMonikerSet gIDMonikerSet) {
        this.monikers = gIDMonikerSet;
    }

    public GIDMonikerSet getFullMonikerSet() {
        return fullMonikerSet;
    }

    public GIDIdentity fullMonikerSet(GIDMonikerSet gIDMonikerSet) {
        this.fullMonikerSet = gIDMonikerSet;
        return this;
    }

    public void setFullMonikerSet(GIDMonikerSet gIDMonikerSet) {
        this.fullMonikerSet = gIDMonikerSet;
    }

    public GIDMonikerSet getStandardMonikerSet() {
        return standardMonikerSet;
    }

    public GIDIdentity standardMonikerSet(GIDMonikerSet gIDMonikerSet) {
        this.standardMonikerSet = gIDMonikerSet;
        return this;
    }

    public void setStandardMonikerSet(GIDMonikerSet gIDMonikerSet) {
        this.standardMonikerSet = gIDMonikerSet;
    }

    public Set<GIDMembership> getMemberships() {
        return memberships;
    }

    public GIDIdentity memberships(Set<GIDMembership> gIDMemberships) {
        this.memberships = gIDMemberships;
        return this;
    }

    public GIDIdentity addMemberships(GIDMembership gIDMembership) {
        this.memberships.add(gIDMembership);
        gIDMembership.getIdentities().add(this);
        return this;
    }

    public GIDIdentity removeMemberships(GIDMembership gIDMembership) {
        this.memberships.remove(gIDMembership);
        gIDMembership.getIdentities().remove(this);
        return this;
    }

    public void setMemberships(Set<GIDMembership> gIDMemberships) {
        this.memberships = gIDMemberships;
    }

    public Set<GIDUser> getUsers() {
        return users;
    }

    public GIDIdentity users(Set<GIDUser> gIDUsers) {
        this.users = gIDUsers;
        return this;
    }

    public GIDIdentity addUser(GIDUser gIDUser) {
        this.users.add(gIDUser);
        gIDUser.getIdentities().add(this);
        return this;
    }

    public GIDIdentity removeUser(GIDUser gIDUser) {
        this.users.remove(gIDUser);
        gIDUser.getIdentities().remove(this);
        return this;
    }

    public void setUsers(Set<GIDUser> gIDUsers) {
        this.users = gIDUsers;
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
