package com.test.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToOne
    @JoinColumn(unique = true)
    private GIDMonikerSet monikers;

    @ManyToMany(mappedBy = "memberships")
    @JsonIgnore
    private Set<GIDIdentity> identities = new HashSet<>();

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

    public GIDMonikerSet getMonikers() {
        return monikers;
    }

    public GIDMembership monikers(GIDMonikerSet gIDMonikerSet) {
        this.monikers = gIDMonikerSet;
        return this;
    }

    public void setMonikers(GIDMonikerSet gIDMonikerSet) {
        this.monikers = gIDMonikerSet;
    }

    public Set<GIDIdentity> getIdentities() {
        return identities;
    }

    public GIDMembership identities(Set<GIDIdentity> gIDIdentities) {
        this.identities = gIDIdentities;
        return this;
    }

    public GIDMembership addIdentity(GIDIdentity gIDIdentity) {
        this.identities.add(gIDIdentity);
        gIDIdentity.getMemberships().add(this);
        return this;
    }

    public GIDMembership removeIdentity(GIDIdentity gIDIdentity) {
        this.identities.remove(gIDIdentity);
        gIDIdentity.getMemberships().remove(this);
        return this;
    }

    public void setIdentities(Set<GIDIdentity> gIDIdentities) {
        this.identities = gIDIdentities;
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
