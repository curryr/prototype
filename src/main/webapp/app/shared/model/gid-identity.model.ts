import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';
import { IGIDUser } from 'app/shared/model/gid-user.model';

export interface IGIDIdentity {
  id?: number;
  gid?: string;
  pgid?: string;
  gIDMemberships?: IGIDMembership[];
  standardMonikerSets?: IGIDMoniker[];
  fullMonikerSets?: IGIDMoniker[];
  gIDMonikers?: IGIDMoniker[];
  user?: IGIDUser;
}

export class GIDIdentity implements IGIDIdentity {
  constructor(
    public id?: number,
    public gid?: string,
    public pgid?: string,
    public gIDMemberships?: IGIDMembership[],
    public standardMonikerSets?: IGIDMoniker[],
    public fullMonikerSets?: IGIDMoniker[],
    public gIDMonikers?: IGIDMoniker[],
    public user?: IGIDUser
  ) {}
}
