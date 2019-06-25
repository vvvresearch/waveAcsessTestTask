import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRoom, Room } from 'app/shared/model/room.model';
import { RoomService } from './room.service';

@Component({
  selector: 'jhi-room-update',
  templateUrl: './room-update.component.html'
})
export class RoomUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    roomName: [null, [Validators.required]],
    level: [],
    capacity: []
  });

  constructor(protected roomService: RoomService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ room }) => {
      this.updateForm(room);
    });
  }

  updateForm(room: IRoom) {
    this.editForm.patchValue({
      id: room.id,
      roomName: room.roomName,
      level: room.level,
      capacity: room.capacity
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const room = this.createFromForm();
    if (room.id !== undefined) {
      this.subscribeToSaveResponse(this.roomService.update(room));
    } else {
      this.subscribeToSaveResponse(this.roomService.create(room));
    }
  }

  private createFromForm(): IRoom {
    return {
      ...new Room(),
      id: this.editForm.get(['id']).value,
      roomName: this.editForm.get(['roomName']).value,
      level: this.editForm.get(['level']).value,
      capacity: this.editForm.get(['capacity']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoom>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
