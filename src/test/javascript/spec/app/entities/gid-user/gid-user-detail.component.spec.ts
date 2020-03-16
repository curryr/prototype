import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDUserDetailComponent } from 'app/entities/gid-user/gid-user-detail.component';
import { GIDUser } from 'app/shared/model/gid-user.model';

describe('Component Tests', () => {
  describe('GIDUser Management Detail Component', () => {
    let comp: GIDUserDetailComponent;
    let fixture: ComponentFixture<GIDUserDetailComponent>;
    const route = ({ data: of({ gIDUser: new GIDUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GIDUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GIDUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gIDUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gIDUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
