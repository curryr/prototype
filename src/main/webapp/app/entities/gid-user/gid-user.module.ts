import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrototypeSharedModule } from 'app/shared/shared.module';
import { GIDUserComponent } from './gid-user.component';
import { GIDUserDetailComponent } from './gid-user-detail.component';
import { GIDUserUpdateComponent } from './gid-user-update.component';
import { GIDUserDeleteDialogComponent } from './gid-user-delete-dialog.component';
import { gIDUserRoute } from './gid-user.route';

@NgModule({
  imports: [PrototypeSharedModule, RouterModule.forChild(gIDUserRoute)],
  declarations: [GIDUserComponent, GIDUserDetailComponent, GIDUserUpdateComponent, GIDUserDeleteDialogComponent],
  entryComponents: [GIDUserDeleteDialogComponent]
})
export class PrototypeGIDUserModule {}
