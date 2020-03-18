import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { IGIDUser } from 'app/shared/model/gid-user.model';

export interface IGIDIdentity {
  id?: number;
  gid?: string;
  pgid?: string;
  monikers?: IGIDMonikerSet;
  fullMonikerSet?: IGIDMonikerSet;
  standardMonikerSet?: IGIDMonikerSet;
  memberships?: IGIDMembership[];
  users?: IGIDUser[];
}

export class GIDIdentity implements IGIDIdentity {
  constructor(
    public id?: number,
    public gid?: string,
    public pgid?: string,
    public monikers?: IGIDMonikerSet,
    public fullMonikerSet?: IGIDMonikerSet,
    public standardMonikerSet?: IGIDMonikerSet,
    public memberships?: IGIDMembership[],
    public users?: IGIDUser[]
  ) {}
}
