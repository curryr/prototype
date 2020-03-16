import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGIDIdentity, GIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from './gid-identity.service';
import { GIDIdentityComponent } from './gid-identity.component';
import { GIDIdentityDetailComponent } from './gid-identity-detail.component';
import { GIDIdentityUpdateComponent } from './gid-identity-update.component';

@Injectable({ providedIn: 'root' })
export class GIDIdentityResolve implements Resolve<IGIDIdentity> {
  constructor(private service: GIDIdentityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGIDIdentity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gIDIdentity: HttpResponse<GIDIdentity>) => {
          if (gIDIdentity.body) {
            return of(gIDIdentity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GIDIdentity());
  }
}

export const gIDIdentityRoute: Routes = [
  {
    path: '',
    component: GIDIdentityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDIdentities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GIDIdentityDetailComponent,
    resolve: {
      gIDIdentity: GIDIdentityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDIdentities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GIDIdentityUpdateComponent,
    resolve: {
      gIDIdentity: GIDIdentityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDIdentities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GIDIdentityUpdateComponent,
    resolve: {
      gIDIdentity: GIDIdentityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDIdentities'
    },
    canActivate: [UserRouteAccessService]
  }
];
