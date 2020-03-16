import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGIDMonikerSet, GIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerSetService } from './gid-moniker-set.service';
import { GIDMonikerSetComponent } from './gid-moniker-set.component';
import { GIDMonikerSetDetailComponent } from './gid-moniker-set-detail.component';
import { GIDMonikerSetUpdateComponent } from './gid-moniker-set-update.component';

@Injectable({ providedIn: 'root' })
export class GIDMonikerSetResolve implements Resolve<IGIDMonikerSet> {
  constructor(private service: GIDMonikerSetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGIDMonikerSet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gIDMonikerSet: HttpResponse<GIDMonikerSet>) => {
          if (gIDMonikerSet.body) {
            return of(gIDMonikerSet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GIDMonikerSet());
  }
}

export const gIDMonikerSetRoute: Routes = [
  {
    path: '',
    component: GIDMonikerSetComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikerSets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GIDMonikerSetDetailComponent,
    resolve: {
      gIDMonikerSet: GIDMonikerSetResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikerSets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GIDMonikerSetUpdateComponent,
    resolve: {
      gIDMonikerSet: GIDMonikerSetResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikerSets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GIDMonikerSetUpdateComponent,
    resolve: {
      gIDMonikerSet: GIDMonikerSetResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GIDMonikerSets'
    },
    canActivate: [UserRouteAccessService]
  }
];
