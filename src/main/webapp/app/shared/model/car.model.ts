import { IOwner } from 'app/shared/model/owner.model';

export interface ICar {
  id?: number;
  ownedBy?: IOwner;
  owner?: IOwner;
}

export class Car implements ICar {
  constructor(public id?: number, public ownedBy?: IOwner, public owner?: IOwner) {}
}
