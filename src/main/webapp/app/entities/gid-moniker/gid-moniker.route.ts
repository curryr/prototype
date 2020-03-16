import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGIDMoniker, GIDMoniker } from 'app/shared/model/gid-moniker.model';
import { GIDMonikerService } from './gid-moniker.service';
import { GIDMonikerComponent } from './gid-moniker.component';
import { GIDMonikerDetailComponent } from './gid-moniker-detail.component';
import { GIDMonikerUpdateComponent } from './gid-moniker-update.component';

@Injectable({ providedIn: 'root' })
export class GIDMonikerResolve implements Resolve<IGIDMoniker> {
  constructor(private service: GIDMonikerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGIDMoniker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gIDMoniker: HttpResponse<GIDMoniker>) => {
          if (gIDMoniker.body) {
            return of(gIDMoniker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GIDMoniker());
  }
}

export const gIDMonikerRoute: Routes = [
  {
    path: '',
    component: GIDMonikerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GIDMonikerDetailComponent,
    resolve: {
      gIDMoniker: GIDMonikerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GIDMonikerUpdateComponent,
    resolve: {
      gIDMoniker: GIDMonikerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GIDMonikerUpdateComponent,
    resolve: {
      gIDMoniker: GIDMonikerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikers'
    },
    canActivate: [UserRouteAccessService]
  }
];
