import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';

export interface IGIDMonikerSet {
  id?: number;
  gIDMonikers?: IGIDMoniker[];
}

export class GIDMonikerSet implements IGIDMonikerSet {
  constructor(public id?: number, public gIDMonikers?: IGIDMoniker[]) {}
}
