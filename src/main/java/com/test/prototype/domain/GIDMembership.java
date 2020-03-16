package com.test.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A GIDMembership.
 */
@Entity
@Table(name = "gid_membership")
public class GIDMembership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ogid")
    private String ogid;

    @Column(name = "tenant_key")
    private String tenantKey;

    @Column(name = "tenant_user_key")
    private String tenantUserKey;

    @Column(name = "tenant_user_block")
    private String tenantUserBlock;

    @OneToMany(mappedBy = "membership")
    private Set<GIDMoniker> gIDMonikers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("gIDMemberships")
    private GIDIdentity identity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOgid() {
        return ogid;
    }

    public GIDMembership ogid(String ogid) {
        this.ogid = ogid;
        return this;
    }

    public void setOgid(String ogid) {
        this.ogid = ogid;
    }

    public String getTenantKey() {
        return tenantKey;
    }

    public GIDMembership tenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
        return this;
    }

    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }

    public String getTenantUserKey() {
        return tenantUserKey;
    }

    public GIDMembership tenantUserKey(String tenantUserKey) {
        this.tenantUserKey = tenantUserKey;
        return this;
    }

    public void setTenantUserKey(String tenantUserKey) {
        this.tenantUserKey = tenantUserKey;
    }

    public String getTenantUserBlock() {
        return tenantUserBlock;
    }

    public GIDMembership tenantUserBlock(String tenantUserBlock) {
        this.tenantUserBlock = tenantUserBlock;
        return this;
    }

    public void setTenantUserBlock(String tenantUserBlock) {
        this.tenantUserBlock = tenantUserBlock;
    }

    public Set<GIDMoniker> getGIDMonikers() {
        return gIDMonikers;
    }

    public GIDMembership gIDMonikers(Set<GIDMoniker> gIDMonikers) {
        this.gIDMonikers = gIDMonikers;
        return this;
    }

    public GIDMembership addGIDMoniker(GIDMoniker gIDMoniker) {
        this.gIDMonikers.add(gIDMoniker);
        gIDMoniker.setMembership(this);
        return this;
    }

    public GIDMembership removeGIDMoniker(GIDMoniker gIDMoniker) {
        this.gIDMonikers.remove(gIDMoniker);
        gIDMoniker.setMembership(null);
        return this;
    }

    public void setGIDMonikers(Set<GIDMoniker> gIDMonikers) {
        this.gIDMonikers = gIDMonikers;
    }

    public GIDIdentity getIdentity() {
        return identity;
    }

    public GIDMembership identity(GIDIdentity gIDIdentity) {
        this.identity = gIDIdentity;
        return this;
    }

    public void setIdentity(GIDIdentity gIDIdentity) {
        this.identity = gIDIdentity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GIDMembership)) {
            return false;
        }
        return id != null && id.equals(((GIDMembership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GIDMembership{" +
            "id=" + getId() +
            ", ogid='" + getOgid() + "'" +
            ", tenantKey='" + getTenantKey() + "'" +
            ", tenantUserKey='" + getTenantUserKey() + "'" +
            ", tenantUserBlock='" + getTenantUserBlock() + "'" +
            "}";
    }
}
