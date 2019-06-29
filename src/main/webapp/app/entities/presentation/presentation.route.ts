import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Presentation } from 'app/shared/model/presentation.model';
import { PresentationService } from './presentation.service';
import { PresentationComponent } from './presentation.component';
import { PresentationDetailComponent } from './presentation-detail.component';
import { PresentationUpdateComponent } from './presentation-update.component';
import { PresentationDeletePopupComponent } from './presentation-delete-dialog.component';
import { IPresentation } from 'app/shared/model/presentation.model';

@Injectable({ providedIn: 'root' })
export class PresentationResolve implements Resolve<IPresentation> {
  constructor(private service: PresentationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPresentation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Presentation>) => response.ok),
        map((presentation: HttpResponse<Presentation>) => presentation.body)
      );
    }
    return of(new Presentation());
  }
}

export const presentationRoute: Routes = [
  {
    path: '',
    component: PresentationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_LISTENER', 'ROLE_PRESENTER', 'ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'waveAcsessConferenceApp.presentation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PresentationDetailComponent,
    resolve: {
      presentation: PresentationResolve
    },
    data: {
      authorities: ['ROLE_LISTENER', 'ROLE_PRESENTER', 'ROLE_ADMIN'],
      pageTitle: 'waveAcsessConferenceApp.presentation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PresentationUpdateComponent,
    resolve: {
      presentation: PresentationResolve
    },
    data: {
      authorities: ['ROLE_PRESENTER', 'ROLE_ADMIN'],
      pageTitle: 'waveAcsessConferenceApp.presentation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PresentationUpdateComponent,
    resolve: {
      presentation: PresentationResolve
    },
    data: {
      authorities: ['ROLE_PRESENTER', 'ROLE_ADMIN'],
      pageTitle: 'waveAcsessConferenceApp.presentation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const presentationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PresentationDeletePopupComponent,
    resolve: {
      presentation: PresentationResolve
    },
    data: {
      authorities: ['ROLE_PRESENTER', 'ROLE_ADMIN'],
      pageTitle: 'waveAcsessConferenceApp.presentation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
