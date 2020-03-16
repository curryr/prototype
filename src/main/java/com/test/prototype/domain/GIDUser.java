package com.test.prototype.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A GIDUser.
 */
@Entity
@Table(name = "gid_user")
public class GIDUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user")
    private Set<GIDIdentity> gIDIdentities = new HashSet<>();

    @OneToMany(mappedBy = "userOf")
    private Set<GIDMoniker> monickerOfs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public GIDUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GIDUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<GIDIdentity> getGIDIdentities() {
        return gIDIdentities;
    }

    public GIDUser gIDIdentities(Set<GIDIdentity> gIDIdentities) {
        this.gIDIdentities = gIDIdentities;
        return this;
    }

    public GIDUser addGIDIdentity(GIDIdentity gIDIdentity) {
        this.gIDIdentities.add(gIDIdentity);
        gIDIdentity.setUser(this);
        return this;
    }

    public GIDUser removeGIDIdentity(GIDIdentity gIDIdentity) {
        this.gIDIdentities.remove(gIDIdentity);
        gIDIdentity.setUser(null);
        return this;
    }

    public void setGIDIdentities(Set<GIDIdentity> gIDIdentities) {
        this.gIDIdentities = gIDIdentities;
    }

    public Set<GIDMoniker> getMonickerOfs() {
        return monickerOfs;
    }

    public GIDUser monickerOfs(Set<GIDMoniker> gIDMonikers) {
        this.monickerOfs = gIDMonikers;
        return this;
    }

    public GIDUser addMonickerOf(GIDMoniker gIDMoniker) {
        this.monickerOfs.add(gIDMoniker);
        gIDMoniker.setUserOf(this);
        return this;
    }

    public GIDUser removeMonickerOf(GIDMoniker gIDMoniker) {
        this.monickerOfs.remove(gIDMoniker);
        gIDMoniker.setUserOf(null);
        return this;
    }

    public void setMonickerOfs(Set<GIDMoniker> gIDMonikers) {
        this.monickerOfs = gIDMonikers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GIDUser)) {
            return false;
        }
        return id != null && id.equals(((GIDUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GIDUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
