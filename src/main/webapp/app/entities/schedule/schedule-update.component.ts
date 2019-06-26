import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { IPresentation } from 'app/shared/model/presentation.model';
import { PresentationService } from 'app/entities/presentation';
import { IRoom } from 'app/shared/model/room.model';
import { RoomService } from 'app/entities/room';

@Component({
  selector: 'jhi-schedule-update',
  templateUrl: './schedule-update.component.html'
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving: boolean;

  presentations: IPresentation[];

  rooms: IRoom[];
  startTimeDp: any;
  endTimeDp: any;

  editForm = this.fb.group({
    id: [],
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
    presentation: [],
    room: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected scheduleService: ScheduleService,
    protected presentationService: PresentationService,
    protected roomService: RoomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ schedule }) => {
      this.updateForm(schedule);
    });
    this.presentationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPresentation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPresentation[]>) => response.body)
      )
      .subscribe((res: IPresentation[]) => (this.presentations = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.roomService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRoom[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRoom[]>) => response.body)
      )
      .subscribe((res: IRoom[]) => (this.rooms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(schedule: ISchedule) {
    this.editForm.patchValue({
      id: schedule.id,
      startTime: schedule.startTime,
      endTime: schedule.endTime,
      presentation: schedule.presentation,
      room: schedule.room
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  private createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id']).value,
      startTime: this.editForm.get(['startTime']).value,
      endTime: this.editForm.get(['endTime']).value,
      presentation: this.editForm.get(['presentation']).value,
      room: this.editForm.get(['room']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPresentationById(index: number, item: IPresentation) {
    return item.id;
  }

  trackRoomById(index: number, item: IRoom) {
    return item.id;
  }
}
