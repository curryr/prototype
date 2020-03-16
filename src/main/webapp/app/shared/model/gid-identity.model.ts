import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { IGIDUser } from 'app/shared/model/gid-user.model';

export interface IGIDIdentity {
  id?: number;
  gid?: string;
  pgid?: string;
  monickers?: IGIDMonikerSet;
  fullMonikerSet?: IGIDMonikerSet;
  standardMonikerSet?: IGIDMonikerSet;
  gIDMemberships?: IGIDMembership[];
  user?: IGIDUser;
}

export class GIDIdentity implements IGIDIdentity {
  constructor(
    public id?: number,
    public gid?: string,
    public pgid?: string,
    public monickers?: IGIDMonikerSet,
    public fullMonikerSet?: IGIDMonikerSet,
    public standardMonikerSet?: IGIDMonikerSet,
    public gIDMemberships?: IGIDMembership[],
    public user?: IGIDUser
  ) {}
}
