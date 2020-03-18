import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';

export interface IGIDUser {
  id?: number;
  firstName?: string;
  lastName?: string;
  monikers?: IGIDMonikerSet;
  identities?: IGIDIdentity[];
}

export class GIDUser implements IGIDUser {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public monikers?: IGIDMonikerSet,
    public identities?: IGIDIdentity[]
  ) {}
}
