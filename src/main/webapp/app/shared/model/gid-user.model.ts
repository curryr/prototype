import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';

export interface IGIDUser {
  id?: number;
  firstName?: string;
  lastName?: string;
  gIDIdentities?: IGIDIdentity[];
  monickerOfs?: IGIDMoniker[];
}

export class GIDUser implements IGIDUser {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public gIDIdentities?: IGIDIdentity[],
    public monickerOfs?: IGIDMoniker[]
  ) {}
}
