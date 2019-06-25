import { Moment } from 'moment';
import { IRoom } from 'app/shared/model/room.model';

export interface ISchedule {
  id?: number;
  startTime?: Moment;
  endTime?: Moment;
  room?: IRoom;
}

export class Schedule implements ISchedule {
  constructor(public id?: number, public startTime?: Moment, public endTime?: Moment, public room?: IRoom) {}
}
