import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrototypeSharedModule } from 'app/shared/shared.module';
import { GIDMonikerComponent } from './gid-moniker.component';
import { GIDMonikerDetailComponent } from './gid-moniker-detail.component';
import { GIDMonikerUpdateComponent } from './gid-moniker-update.component';
import { GIDMonikerDeleteDialogComponent } from './gid-moniker-delete-dialog.component';
import { gIDMonikerRoute } from './gid-moniker.route';

@NgModule({
  imports: [PrototypeSharedModule, RouterModule.forChild(gIDMonikerRoute)],
  declarations: [GIDMonikerComponent, GIDMonikerDetailComponent, GIDMonikerUpdateComponent, GIDMonikerDeleteDialogComponent],
  entryComponents: [GIDMonikerDeleteDialogComponent]
})
export class PrototypeGIDMonikerModule {}
