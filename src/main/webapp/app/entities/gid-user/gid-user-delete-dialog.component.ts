import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from './gid-user.service';

@Component({
  templateUrl: './gid-user-delete-dialog.component.html'
})
export class GIDUserDeleteDialogComponent {
  gIDUser?: IGIDUser;

  constructor(protected gIDUserService: GIDUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gIDUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gIDUserListModification');
      this.activeModal.close();
    });
  }
}
