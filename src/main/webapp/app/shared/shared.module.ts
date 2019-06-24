import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { WaveAcsessConferenceSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [WaveAcsessConferenceSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [WaveAcsessConferenceSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WaveAcsessConferenceSharedModule {
  static forRoot() {
    return {
      ngModule: WaveAcsessConferenceSharedModule
    };
  }
}
