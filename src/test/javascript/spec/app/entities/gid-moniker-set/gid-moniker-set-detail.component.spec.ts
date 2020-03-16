import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDMonikerSetDetailComponent } from 'app/entities/gid-moniker-set/gid-moniker-set-detail.component';
import { GIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';

describe('Component Tests', () => {
  describe('GIDMonikerSet Management Detail Component', () => {
    let comp: GIDMonikerSetDetailComponent;
    let fixture: ComponentFixture<GIDMonikerSetDetailComponent>;
    const route = ({ data: of({ gIDMonikerSet: new GIDMonikerSet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDMonikerSetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GIDMonikerSetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GIDMonikerSetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gIDMonikerSet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gIDMonikerSet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
