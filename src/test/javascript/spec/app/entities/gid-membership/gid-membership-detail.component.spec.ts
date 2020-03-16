import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDMembershipDetailComponent } from 'app/entities/gid-membership/gid-membership-detail.component';
import { GIDMembership } from 'app/shared/model/gid-membership.model';

describe('Component Tests', () => {
  describe('GIDMembership Management Detail Component', () => {
    let comp: GIDMembershipDetailComponent;
    let fixture: ComponentFixture<GIDMembershipDetailComponent>;
    const route = ({ data: of({ gIDMembership: new GIDMembership(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDMembershipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GIDMembershipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GIDMembershipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gIDMembership on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gIDMembership).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
