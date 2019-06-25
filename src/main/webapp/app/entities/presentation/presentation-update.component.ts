import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPresentation, Presentation } from 'app/shared/model/presentation.model';
import { PresentationService } from './presentation.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-presentation-update',
  templateUrl: './presentation-update.component.html'
})
export class PresentationUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    topic: [null, [Validators.required]],
    description: [],
    users: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected presentationService: PresentationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ presentation }) => {
      this.updateForm(presentation);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(presentation: IPresentation) {
    this.editForm.patchValue({
      id: presentation.id,
      topic: presentation.topic,
      description: presentation.description,
      users: presentation.users
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const presentation = this.createFromForm();
    if (presentation.id !== undefined) {
      this.subscribeToSaveResponse(this.presentationService.update(presentation));
    } else {
      this.subscribeToSaveResponse(this.presentationService.create(presentation));
    }
  }

  private createFromForm(): IPresentation {
    return {
      ...new Presentation(),
      id: this.editForm.get(['id']).value,
      topic: this.editForm.get(['topic']).value,
      description: this.editForm.get(['description']).value,
      users: this.editForm.get(['users']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPresentation>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
