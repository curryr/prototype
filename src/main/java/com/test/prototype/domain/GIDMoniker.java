package com.test.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

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

    @ManyToOne
    @JsonIgnoreProperties("gIDMonikers")
    private GIDMembership membership;

    @ManyToOne
    @JsonIgnoreProperties("gIDMonikers")
    private GIDUser user;

    @ManyToOne
    @JsonIgnoreProperties("gIDMonikers")
    private GIDIdentity identity;

    @ManyToOne
    @JsonIgnoreProperties("standardMonikerSets")
    private GIDIdentity gIDIdentity;

    @ManyToOne
    @JsonIgnoreProperties("standardMonikerSets")
    private GIDIdentity gIDIdentity;

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

    public GIDMembership getMembership() {
        return membership;
    }

    public GIDMoniker membership(GIDMembership gIDMembership) {
        this.membership = gIDMembership;
        return this;
    }

    public void setMembership(GIDMembership gIDMembership) {
        this.membership = gIDMembership;
    }

    public GIDUser getUser() {
        return user;
    }

    public GIDMoniker user(GIDUser gIDUser) {
        this.user = gIDUser;
        return this;
    }

    public void setUser(GIDUser gIDUser) {
        this.user = gIDUser;
    }

    public GIDIdentity getIdentity() {
        return identity;
    }

    public GIDMoniker identity(GIDIdentity gIDIdentity) {
        this.identity = gIDIdentity;
        return this;
    }

    public void setIdentity(GIDIdentity gIDIdentity) {
        this.identity = gIDIdentity;
    }

    public GIDIdentity getGIDIdentity() {
        return gIDIdentity;
    }

    public GIDMoniker gIDIdentity(GIDIdentity gIDIdentity) {
        this.gIDIdentity = gIDIdentity;
        return this;
    }

    public void setGIDIdentity(GIDIdentity gIDIdentity) {
        this.gIDIdentity = gIDIdentity;
    }

    public GIDIdentity getGIDIdentity() {
        return gIDIdentity;
    }

    public GIDMoniker gIDIdentity(GIDIdentity gIDIdentity) {
        this.gIDIdentity = gIDIdentity;
        return this;
    }

    public void setGIDIdentity(GIDIdentity gIDIdentity) {
        this.gIDIdentity = gIDIdentity;
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
