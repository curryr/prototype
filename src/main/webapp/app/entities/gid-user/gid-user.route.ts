import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGIDUser, GIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from './gid-user.service';
import { GIDUserComponent } from './gid-user.component';
import { GIDUserDetailComponent } from './gid-user-detail.component';
import { GIDUserUpdateComponent } from './gid-user-update.component';

@Injectable({ providedIn: 'root' })
export class GIDUserResolve implements Resolve<IGIDUser> {
  constructor(private service: GIDUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGIDUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gIDUser: HttpResponse<GIDUser>) => {
          if (gIDUser.body) {
            return of(gIDUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GIDUser());
  }
}

export const gIDUserRoute: Routes = [
  {
    path: '',
    component: GIDUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GIDUserDetailComponent,
    resolve: {
      gIDUser: GIDUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GIDUserUpdateComponent,
    resolve: {
      gIDUser: GIDUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GIDUserUpdateComponent,
    resolve: {
      gIDUser: GIDUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDUsers'
    },
    canActivate: [UserRouteAccessService]
  }
];
