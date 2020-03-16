import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerPrefix } from 'app/shared/model/enumerations/gid-moniker-prefix.model';

export interface IGIDMoniker {
  id?: number;
  moniker?: string;
  prefix?: GIDMonikerPrefix;
  sets?: IGIDMonikerSet[];
}

export class GIDMoniker implements IGIDMoniker {
  constructor(public id?: number, public moniker?: string, public prefix?: GIDMonikerPrefix, public sets?: IGIDMonikerSet[]) {}
}
