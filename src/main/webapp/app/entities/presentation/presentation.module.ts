import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WaveAcsessConferenceSharedModule } from 'app/shared';
import {
  PresentationComponent,
  PresentationDetailComponent,
  PresentationUpdateComponent,
  PresentationDeletePopupComponent,
  PresentationDeleteDialogComponent,
  presentationRoute,
  presentationPopupRoute
} from './';

const ENTITY_STATES = [...presentationRoute, ...presentationPopupRoute];

@NgModule({
  imports: [WaveAcsessConferenceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PresentationComponent,
    PresentationDetailComponent,
    PresentationUpdateComponent,
    PresentationDeleteDialogComponent,
    PresentationDeletePopupComponent
  ],
  entryComponents: [
    PresentationComponent,
    PresentationUpdateComponent,
    PresentationDeleteDialogComponent,
    PresentationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WaveAcsessConferencePresentationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
