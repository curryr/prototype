import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrototypeSharedModule } from 'app/shared/shared.module';
import { GIDMembershipComponent } from './gid-membership.component';
import { GIDMembershipDetailComponent } from './gid-membership-detail.component';
import { GIDMembershipUpdateComponent } from './gid-membership-update.component';
import { GIDMembershipDeleteDialogComponent } from './gid-membership-delete-dialog.component';
import { gIDMembershipRoute } from './gid-membership.route';

@NgModule({
  imports: [PrototypeSharedModule, RouterModule.forChild(gIDMembershipRoute)],
  declarations: [GIDMembershipComponent, GIDMembershipDetailComponent, GIDMembershipUpdateComponent, GIDMembershipDeleteDialogComponent],
  entryComponents: [GIDMembershipDeleteDialogComponent]
})
export class PrototypeGIDMembershipModule {}
