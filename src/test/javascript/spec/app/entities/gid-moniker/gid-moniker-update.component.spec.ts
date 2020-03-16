import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDMonikerUpdateComponent } from 'app/entities/gid-moniker/gid-moniker-update.component';
import { GIDMonikerService } from 'app/entities/gid-moniker/gid-moniker.service';
import { GIDMoniker } from 'app/shared/model/gid-moniker.model';

describe('Component Tests', () => {
  describe('GIDMoniker Management Update Component', () => {
    let comp: GIDMonikerUpdateComponent;
    let fixture: ComponentFixture<GIDMonikerUpdateComponent>;
    let service: GIDMonikerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDMonikerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GIDMonikerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GIDMonikerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GIDMonikerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GIDMoniker(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GIDMoniker();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
