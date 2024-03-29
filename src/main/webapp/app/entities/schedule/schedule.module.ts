import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WaveAcsessConferenceSharedModule } from 'app/shared';
import {
  ScheduleComponent,
  ScheduleDetailComponent,
  ScheduleUpdateComponent,
  ScheduleDeletePopupComponent,
  ScheduleDeleteDialogComponent,
  scheduleRoute,
  schedulePopupRoute
} from './';

const ENTITY_STATES = [...scheduleRoute, ...schedulePopupRoute];

@NgModule({
  imports: [WaveAcsessConferenceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ScheduleComponent,
    ScheduleDetailComponent,
    ScheduleUpdateComponent,
    ScheduleDeleteDialogComponent,
    ScheduleDeletePopupComponent
  ],
  entryComponents: [ScheduleComponent, ScheduleUpdateComponent, ScheduleDeleteDialogComponent, ScheduleDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  exports: [ScheduleDetailComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WaveAcsessConferenceScheduleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
