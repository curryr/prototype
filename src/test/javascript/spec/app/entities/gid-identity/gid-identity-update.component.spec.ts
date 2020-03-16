import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDIdentityUpdateComponent } from 'app/entities/gid-identity/gid-identity-update.component';
import { GIDIdentityService } from 'app/entities/gid-identity/gid-identity.service';
import { GIDIdentity } from 'app/shared/model/gid-identity.model';

describe('Component Tests', () => {
  describe('GIDIdentity Management Update Component', () => {
    let comp: GIDIdentityUpdateComponent;
    let fixture: ComponentFixture<GIDIdentityUpdateComponent>;
    let service: GIDIdentityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDIdentityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GIDIdentityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GIDIdentityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GIDIdentityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GIDIdentity(123);
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
        const entity = new GIDIdentity();
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
