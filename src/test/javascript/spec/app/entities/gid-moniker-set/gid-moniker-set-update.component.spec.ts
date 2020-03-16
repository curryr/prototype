import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDMonikerSetUpdateComponent } from 'app/entities/gid-moniker-set/gid-moniker-set-update.component';
import { GIDMonikerSetService } from 'app/entities/gid-moniker-set/gid-moniker-set.service';
import { GIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';

describe('Component Tests', () => {
  describe('GIDMonikerSet Management Update Component', () => {
    let comp: GIDMonikerSetUpdateComponent;
    let fixture: ComponentFixture<GIDMonikerSetUpdateComponent>;
    let service: GIDMonikerSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDMonikerSetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GIDMonikerSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GIDMonikerSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GIDMonikerSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GIDMonikerSet(123);
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
        const entity = new GIDMonikerSet();
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
