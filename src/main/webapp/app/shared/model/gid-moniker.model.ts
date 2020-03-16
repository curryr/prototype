import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { IGIDUser } from 'app/shared/model/gid-user.model';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDMonikerPrefix } from 'app/shared/model/enumerations/gid-moniker-prefix.model';

export interface IGIDMoniker {
  id?: number;
  moniker?: string;
  prefix?: GIDMonikerPrefix;
  membership?: IGIDMembership;
  user?: IGIDUser;
  identity?: IGIDIdentity;
  gIDIdentity?: IGIDIdentity;
  gIDIdentity?: IGIDIdentity;
}

export class GIDMoniker implements IGIDMoniker {
  constructor(
    public id?: number,
    public moniker?: string,
    public prefix?: GIDMonikerPrefix,
    public membership?: IGIDMembership,
    public user?: IGIDUser,
    public identity?: IGIDIdentity,
    public gIDIdentity?: IGIDIdentity,
    public gIDIdentity?: IGIDIdentity
  ) {}
}
