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
    @JsonIgnoreProperties("monickerOfs")
    private GIDMembership userOf;

    @ManyToOne
    @JsonIgnoreProperties("monickerOfs")
    private GIDUser userOf;

    @ManyToOne
    @JsonIgnoreProperties("monickerOfs")
    private GIDIdentity userOf;

    @ManyToOne
    @JsonIgnoreProperties("standardMonikerSets")
    private GIDIdentity contains;

    @ManyToOne
    @JsonIgnoreProperties("standardMonikerSets")
    private GIDIdentity contains;

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

    public GIDMembership getUserOf() {
        return userOf;
    }

    public GIDMoniker userOf(GIDMembership gIDMembership) {
        this.userOf = gIDMembership;
        return this;
    }

    public void setUserOf(GIDMembership gIDMembership) {
        this.userOf = gIDMembership;
    }

    public GIDUser getUserOf() {
        return userOf;
    }

    public GIDMoniker userOf(GIDUser gIDUser) {
        this.userOf = gIDUser;
        return this;
    }

    public void setUserOf(GIDUser gIDUser) {
        this.userOf = gIDUser;
    }

    public GIDIdentity getUserOf() {
        return userOf;
    }

    public GIDMoniker userOf(GIDIdentity gIDIdentity) {
        this.userOf = gIDIdentity;
        return this;
    }

    public void setUserOf(GIDIdentity gIDIdentity) {
        this.userOf = gIDIdentity;
    }

    public GIDIdentity getContains() {
        return contains;
    }

    public GIDMoniker contains(GIDIdentity gIDIdentity) {
        this.contains = gIDIdentity;
        return this;
    }

    public void setContains(GIDIdentity gIDIdentity) {
        this.contains = gIDIdentity;
    }

    public GIDIdentity getContains() {
        return contains;
    }

    public GIDMoniker contains(GIDIdentity gIDIdentity) {
        this.contains = gIDIdentity;
        return this;
    }

    public void setContains(GIDIdentity gIDIdentity) {
        this.contains = gIDIdentity;
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
