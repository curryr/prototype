import { IOwner } from 'app/shared/model/owner.model';

export interface ICar {
  id?: number;
  model?: string;
  year?: string;
  ownedBy?: IOwner;
  owner?: IOwner;
}

export class Car implements ICar {
  constructor(public id?: number, public model?: string, public year?: string, public ownedBy?: IOwner, public owner?: IOwner) {}
}
