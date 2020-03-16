import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from './gid-identity.service';

@Component({
  templateUrl: './gid-identity-delete-dialog.component.html'
})
export class GIDIdentityDeleteDialogComponent {
  gIDIdentity?: IGIDIdentity;

  constructor(
    protected gIDIdentityService: GIDIdentityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gIDIdentityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gIDIdentityListModification');
      this.activeModal.close();
    });
  }
}
