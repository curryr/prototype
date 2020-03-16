import { ICar } from 'app/shared/model/car.model';

export interface IOwner {
  id?: number;
  name?: string;
  cars?: ICar[];
}

export class Owner implements IOwner {
  constructor(public id?: number, public name?: string, public cars?: ICar[]) {}
}
