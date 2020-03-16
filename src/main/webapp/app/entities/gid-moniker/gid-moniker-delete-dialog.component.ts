import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';
import { GIDMonikerService } from './gid-moniker.service';

@Component({
  templateUrl: './gid-moniker-delete-dialog.component.html'
})
export class GIDMonikerDeleteDialogComponent {
  gIDMoniker?: IGIDMoniker;

  constructor(
    protected gIDMonikerService: GIDMonikerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gIDMonikerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gIDMonikerListModification');
      this.activeModal.close();
    });
  }
}
