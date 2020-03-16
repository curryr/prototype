import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerSetService } from './gid-moniker-set.service';

@Component({
  templateUrl: './gid-moniker-set-delete-dialog.component.html'
})
export class GIDMonikerSetDeleteDialogComponent {
  gIDMonikerSet?: IGIDMonikerSet;

  constructor(
    protected gIDMonikerSetService: GIDMonikerSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gIDMonikerSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gIDMonikerSetListModification');
      this.activeModal.close();
    });
  }
}
