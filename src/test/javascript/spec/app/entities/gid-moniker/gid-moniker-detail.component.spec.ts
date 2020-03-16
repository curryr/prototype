import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { GIDMonikerDetailComponent } from 'app/entities/gid-moniker/gid-moniker-detail.component';
import { GIDMoniker } from 'app/shared/model/gid-moniker.model';

describe('Component Tests', () => {
  describe('GIDMoniker Management Detail Component', () => {
    let comp: GIDMonikerDetailComponent;
    let fixture: ComponentFixture<GIDMonikerDetailComponent>;
    const route = ({ data: of({ gIDMoniker: new GIDMoniker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDMonikerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GIDMonikerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GIDMonikerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gIDMoniker on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gIDMoniker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
