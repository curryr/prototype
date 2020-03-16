import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PrototypeTestModule } from '../../../test.module';
import { GIDMonikerSetComponent } from 'app/entities/gid-moniker-set/gid-moniker-set.component';
import { GIDMonikerSetService } from 'app/entities/gid-moniker-set/gid-moniker-set.service';
import { GIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';

describe('Component Tests', () => {
  describe('GIDMonikerSet Management Component', () => {
    let comp: GIDMonikerSetComponent;
    let fixture: ComponentFixture<GIDMonikerSetComponent>;
    let service: GIDMonikerSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrototypeTestModule],
        declarations: [GIDMonikerSetComponent]
      })
        .overrideTemplate(GIDMonikerSetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GIDMonikerSetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GIDMonikerSetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GIDMonikerSet(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gIDMonikerSets && comp.gIDMonikerSets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
