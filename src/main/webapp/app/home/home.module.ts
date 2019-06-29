import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WaveAcsessConferenceSharedModule } from 'app/shared';
import { HOME_ROUTE } from 'app/home/home.route';
import { HomeComponent } from 'app/home/home.component';

@NgModule({
  imports: [WaveAcsessConferenceSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WaveAcsessConferenceHomeModule {}
