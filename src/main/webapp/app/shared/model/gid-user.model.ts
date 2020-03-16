import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';

export interface IGIDUser {
  id?: number;
  firstName?: string;
  lastName?: string;
  monickers?: IGIDMonikerSet;
  gIDIdentities?: IGIDIdentity[];
}

export class GIDUser implements IGIDUser {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public monickers?: IGIDMonikerSet,
    public gIDIdentities?: IGIDIdentity[]
  ) {}
}
