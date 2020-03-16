import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDMembershipUpdateComponent } from 'app/entities/gid-membership/gid-membership-update.component';
import { GIDMembershipService } from 'app/entities/gid-membership/gid-membership.service';
import { GIDMembership } from 'app/shared/model/gid-membership.model';

describe('Component Tests', () => {
  describe('GIDMembership Management Update Component', () => {
    let comp: GIDMembershipUpdateComponent;
    let fixture: ComponentFixture<GIDMembershipUpdateComponent>;
    let service: GIDMembershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDMembershipUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GIDMembershipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GIDMembershipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GIDMembershipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GIDMembership(123);
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
        const entity = new GIDMembership();
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
