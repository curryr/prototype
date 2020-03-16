import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';

export interface IGIDMembership {
  id?: number;
  ogid?: string;
  tenantKey?: string;
  tenantUserKey?: string;
  tenantUserBlock?: string;
  gIDMonikers?: IGIDMoniker[];
  identity?: IGIDIdentity;
}

export class GIDMembership implements IGIDMembership {
  constructor(
    public id?: number,
    public ogid?: string,
    public tenantKey?: string,
    public tenantUserKey?: string,
    public tenantUserBlock?: string,
    public gIDMonikers?: IGIDMoniker[],
    public identity?: IGIDIdentity
  ) {}
}
