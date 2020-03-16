import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDIdentityDetailComponent } from 'app/entities/gid-identity/gid-identity-detail.component';
import { GIDIdentity } from 'app/shared/model/gid-identity.model';

describe('Component Tests', () => {
  describe('GIDIdentity Management Detail Component', () => {
    let comp: GIDIdentityDetailComponent;
    let fixture: ComponentFixture<GIDIdentityDetailComponent>;
    const route = ({ data: of({ gIDIdentity: new GIDIdentity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDIdentityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GIDIdentityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GIDIdentityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gIDIdentity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gIDIdentity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
