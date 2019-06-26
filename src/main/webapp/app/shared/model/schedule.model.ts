import { Moment } from 'moment';
import { IPresentation } from 'app/shared/model/presentation.model';
import { IRoom } from 'app/shared/model/room.model';

export interface ISchedule {
  id?: number;
  startTime?: Moment;
  endTime?: Moment;
  presentation?: IPresentation;
  room?: IRoom;
}

export class Schedule implements ISchedule {
  constructor(
    public id?: number,
    public startTime?: Moment,
    public endTime?: Moment,
    public presentation?: IPresentation,
    public room?: IRoom
  ) {}
}
