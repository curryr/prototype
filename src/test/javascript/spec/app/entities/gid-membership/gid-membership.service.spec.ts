import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GIDMembershipService } from 'app/entities/gid-membership/gid-membership.service';
import { IGIDMembership, GIDMembership } from 'app/shared/model/gid-membership.model';

describe('Service Tests', () => {
  describe('GIDMembership Service', () => {
    let injector: TestBed;
    let service: GIDMembershipService;
    let httpMock: HttpTestingController;
    let elemDefault: IGIDMembership;
    let expectedResult: IGIDMembership | IGIDMembership[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GIDMembershipService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new GIDMembership(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GIDMembership', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new GIDMembership()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GIDMembership', () => {
        const returnedFromService = Object.assign(
          {
            ogid: 'BBBBBB',
            tenantKey: 'BBBBBB',
            tenantUserKey: 'BBBBBB',
            tenantUserBlock: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GIDMembership', () => {
        const returnedFromService = Object.assign(
          {
            ogid: 'BBBBBB',
            tenantKey: 'BBBBBB',
            tenantUserKey: 'BBBBBB',
            tenantUserBlock: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GIDMembership', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
