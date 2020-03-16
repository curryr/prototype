import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGIDMembership } from 'app/shared/model/gid-membership.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GIDMembershipService } from './gid-membership.service';
import { GIDMembershipDeleteDialogComponent } from './gid-membership-delete-dialog.component';

@Component({
  selector: 'jhi-gid-membership',
  templateUrl: './gid-membership.component.html'
})
export class GIDMembershipComponent implements OnInit, OnDestroy {
  gIDMemberships: IGIDMembership[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected gIDMembershipService: GIDMembershipService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gIDMemberships = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.gIDMembershipService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGIDMembership[]>) => this.paginateGIDMemberships(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.gIDMemberships = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGIDMemberships();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGIDMembership): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGIDMemberships(): void {
    this.eventSubscriber = this.eventManager.subscribe('gIDMembershipListModification', () => this.reset());
  }

  delete(gIDMembership: IGIDMembership): void {
    const modalRef = this.modalService.open(GIDMembershipDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gIDMembership = gIDMembership;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGIDMemberships(data: IGIDMembership[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.gIDMemberships.push(data[i]);
      }
    }
  }
}
