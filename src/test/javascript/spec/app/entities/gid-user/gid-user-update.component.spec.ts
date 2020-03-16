import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDUserUpdateComponent } from 'app/entities/gid-user/gid-user-update.component';
import { GIDUserService } from 'app/entities/gid-user/gid-user.service';
import { GIDUser } from 'app/shared/model/gid-user.model';

describe('Component Tests', () => {
  describe('GIDUser Management Update Component', () => {
    let comp: GIDUserUpdateComponent;
    let fixture: ComponentFixture<GIDUserUpdateComponent>;
    let service: GIDUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GIDUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GIDUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GIDUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GIDUser(123);
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
        const entity = new GIDUser();
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
