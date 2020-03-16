import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { GIDMembershipService } from './gid-membership.service';

@Component({
  templateUrl: './gid-membership-delete-dialog.component.html'
})
export class GIDMembershipDeleteDialogComponent {
  gIDMembership?: IGIDMembership;

  constructor(
    protected gIDMembershipService: GIDMembershipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gIDMembershipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gIDMembershipListModification');
      this.activeModal.close();
    });
  }
}
