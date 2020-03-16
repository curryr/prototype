import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrototypeSharedModule } from 'app/shared/shared.module';
import { GIDIdentityComponent } from './gid-identity.component';
import { GIDIdentityDetailComponent } from './gid-identity-detail.component';
import { GIDIdentityUpdateComponent } from './gid-identity-update.component';
import { GIDIdentityDeleteDialogComponent } from './gid-identity-delete-dialog.component';
import { gIDIdentityRoute } from './gid-identity.route';

@NgModule({
  imports: [PrototypeSharedModule, RouterModule.forChild(gIDIdentityRoute)],
  declarations: [GIDIdentityComponent, GIDIdentityDetailComponent, GIDIdentityUpdateComponent, GIDIdentityDeleteDialogComponent],
  entryComponents: [GIDIdentityDeleteDialogComponent]
})
export class PrototypeGIDIdentityModule {}
