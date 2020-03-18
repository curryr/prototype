import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';

export interface IGIDMembership {
  id?: number;
  ogid?: string;
  tenantKey?: string;
  tenantUserKey?: string;
  tenantUserBlock?: string;
  monikers?: IGIDMonikerSet;
  identity?: IGIDIdentity;
}

export class GIDMembership implements IGIDMembership {
  constructor(
    public id?: number,
    public ogid?: string,
    public tenantKey?: string,
    public tenantUserKey?: string,
    public tenantUserBlock?: string,
    public monikers?: IGIDMonikerSet,
    public identity?: IGIDIdentity
  ) {}
}
