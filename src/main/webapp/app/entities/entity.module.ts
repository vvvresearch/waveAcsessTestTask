import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'room',
        loadChildren: './room/room.module#WaveAcsessConferenceRoomModule'
      },
      {
        path: 'schedule',
        loadChildren: './schedule/schedule.module#WaveAcsessConferenceScheduleModule'
      },
      {
        path: 'presentation',
        loadChildren: './presentation/presentation.module#WaveAcsessConferencePresentationModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WaveAcsessConferenceEntityModule {}
