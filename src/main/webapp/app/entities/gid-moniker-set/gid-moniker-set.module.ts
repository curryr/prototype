import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrototypeSharedModule } from 'app/shared/shared.module';
import { GIDMonikerSetComponent } from './gid-moniker-set.component';
import { GIDMonikerSetDetailComponent } from './gid-moniker-set-detail.component';
import { GIDMonikerSetUpdateComponent } from './gid-moniker-set-update.component';
import { GIDMonikerSetDeleteDialogComponent } from './gid-moniker-set-delete-dialog.component';
import { gIDMonikerSetRoute } from './gid-moniker-set.route';

@NgModule({
  imports: [PrototypeSharedModule, RouterModule.forChild(gIDMonikerSetRoute)],
  declarations: [GIDMonikerSetComponent, GIDMonikerSetDetailComponent, GIDMonikerSetUpdateComponent, GIDMonikerSetDeleteDialogComponent],
  entryComponents: [GIDMonikerSetDeleteDialogComponent]
})
export class PrototypeGIDMonikerSetModule {}
