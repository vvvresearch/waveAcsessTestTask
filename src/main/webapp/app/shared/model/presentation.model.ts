import { IUser } from 'app/core/user/user.model';

export interface IPresentation {
  id?: number;
  topic?: string;
  description?: string;
  users?: IUser[];
}

export class Presentation implements IPresentation {
  constructor(public id?: number, public topic?: string, public description?: string, public users?: IUser[]) {}
}
