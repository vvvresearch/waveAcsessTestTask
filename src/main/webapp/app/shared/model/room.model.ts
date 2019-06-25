import { ISchedule } from 'app/shared/model/schedule.model';

export interface IRoom {
  id?: number;
  roomName?: string;
  level?: number;
  capacity?: number;
  schedules?: ISchedule[];
}

export class Room implements IRoom {
  constructor(
    public id?: number,
    public roomName?: string,
    public level?: number,
    public capacity?: number,
    public schedules?: ISchedule[]
  ) {}
}
