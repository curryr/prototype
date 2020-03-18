import { ICar } from 'app/shared/model/car.model';

export interface IOwner {
  id?: number;
  cars?: ICar[];
}

export class Owner implements IOwner {
  constructor(public id?: number, public cars?: ICar[]) {}
}
