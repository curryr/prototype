import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'gid-user',
        loadChildren: () => import('./gid-user/gid-user.module').then(m => m.PrototypeGIDUserModule)
      },
      {
        path: 'gid-identity',
        loadChildren: () => import('./gid-identity/gid-identity.module').then(m => m.PrototypeGIDIdentityModule)
      },
      {
        path: 'gid-membership',
        loadChildren: () => import('./gid-membership/gid-membership.module').then(m => m.PrototypeGIDMembershipModule)
      },
      {
        path: 'gid-moniker',
        loadChildren: () => import('./gid-moniker/gid-moniker.module').then(m => m.PrototypeGIDMonikerModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PrototypeEntityModule {}
