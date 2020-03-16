import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { IGIDUser } from 'app/shared/model/gid-user.model';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDMonikerPrefix } from 'app/shared/model/enumerations/gid-moniker-prefix.model';

export interface IGIDMoniker {
  id?: number;
  moniker?: string;
  prefix?: GIDMonikerPrefix;
  userOf?: IGIDMembership;
  userOf?: IGIDUser;
  userOf?: IGIDIdentity;
  contains?: IGIDIdentity;
  contains?: IGIDIdentity;
}

export class GIDMoniker implements IGIDMoniker {
  constructor(
    public id?: number,
    public moniker?: string,
    public prefix?: GIDMonikerPrefix,
    public userOf?: IGIDMembership,
    public userOf?: IGIDUser,
    public userOf?: IGIDIdentity,
    public contains?: IGIDIdentity,
    public contains?: IGIDIdentity
  ) {}
}
