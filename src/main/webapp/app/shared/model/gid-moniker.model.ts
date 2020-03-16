import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { IGIDUser } from 'app/shared/model/gid-user.model';
import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { GIDMonikerPrefix } from 'app/shared/model/enumerations/gid-moniker-prefix.model';

export interface IGIDMoniker {
  id?: number;
  moniker?: string;
  prefix?: GIDMonikerPrefix;
  gIDIdentity?: IGIDIdentity;
  gIDIdentity?: IGIDIdentity;
  user?: IGIDUser;
  user?: IGIDIdentity;
  membership?: IGIDMembership;
}

export class GIDMoniker implements IGIDMoniker {
  constructor(
    public id?: number,
    public moniker?: string,
    public prefix?: GIDMonikerPrefix,
    public gIDIdentity?: IGIDIdentity,
    public gIDIdentity?: IGIDIdentity,
    public user?: IGIDUser,
    public user?: IGIDIdentity,
    public membership?: IGIDMembership
  ) {}
}
