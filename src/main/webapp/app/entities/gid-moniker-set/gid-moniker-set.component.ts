import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerSetService } from './gid-moniker-set.service';
import { GIDMonikerSetDeleteDialogComponent } from './gid-moniker-set-delete-dialog.component';

@Component({
  selector: 'jhi-gid-moniker-set',
  templateUrl: './gid-moniker-set.component.html'
})
export class GIDMonikerSetComponent implements OnInit, OnDestroy {
  gIDMonikerSets?: IGIDMonikerSet[];
  eventSubscriber?: Subscription;

  constructor(
    protected gIDMonikerSetService: GIDMonikerSetService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.gIDMonikerSetService.query().subscribe((res: HttpResponse<IGIDMonikerSet[]>) => (this.gIDMonikerSets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGIDMonikerSets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGIDMonikerSet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGIDMonikerSets(): void {
    this.eventSubscriber = this.eventManager.subscribe('gIDMonikerSetListModification', () => this.loadAll());
  }

  delete(gIDMonikerSet: IGIDMonikerSet): void {
    const modalRef = this.modalService.open(GIDMonikerSetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gIDMonikerSet = gIDMonikerSet;
  }
}
