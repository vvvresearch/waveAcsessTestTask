/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WaveAcsessConferenceTestModule } from '../../../test.module';
import { PresentationDeleteDialogComponent } from 'app/entities/presentation/presentation-delete-dialog.component';
import { PresentationService } from 'app/entities/presentation/presentation.service';

describe('Component Tests', () => {
  describe('Presentation Management Delete Component', () => {
    let comp: PresentationDeleteDialogComponent;
    let fixture: ComponentFixture<PresentationDeleteDialogComponent>;
    let service: PresentationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WaveAcsessConferenceTestModule],
        declarations: [PresentationDeleteDialogComponent]
      })
        .overrideTemplate(PresentationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PresentationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PresentationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
