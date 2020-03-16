import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GIDMonikerService } from './gid-moniker.service';
import { GIDMonikerDeleteDialogComponent } from './gid-moniker-delete-dialog.component';

@Component({
  selector: 'jhi-gid-moniker',
  templateUrl: './gid-moniker.component.html'
})
export class GIDMonikerComponent implements OnInit, OnDestroy {
  gIDMonikers: IGIDMoniker[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected gIDMonikerService: GIDMonikerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gIDMonikers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.gIDMonikerService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGIDMoniker[]>) => this.paginateGIDMonikers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.gIDMonikers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGIDMonikers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGIDMoniker): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGIDMonikers(): void {
    this.eventSubscriber = this.eventManager.subscribe('gIDMonikerListModification', () => this.reset());
  }

  delete(gIDMoniker: IGIDMoniker): void {
    const modalRef = this.modalService.open(GIDMonikerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gIDMoniker = gIDMoniker;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGIDMonikers(data: IGIDMoniker[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.gIDMonikers.push(data[i]);
      }
    }
  }
}
