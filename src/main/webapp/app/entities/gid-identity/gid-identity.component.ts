import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGIDIdentity } from 'app/shared/model/gid-identity.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GIDIdentityService } from './gid-identity.service';
import { GIDIdentityDeleteDialogComponent } from './gid-identity-delete-dialog.component';

@Component({
  selector: 'jhi-gid-identity',
  templateUrl: './gid-identity.component.html'
})
export class GIDIdentityComponent implements OnInit, OnDestroy {
  gIDIdentities: IGIDIdentity[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected gIDIdentityService: GIDIdentityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gIDIdentities = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.gIDIdentityService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGIDIdentity[]>) => this.paginateGIDIdentities(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.gIDIdentities = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGIDIdentities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGIDIdentity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGIDIdentities(): void {
    this.eventSubscriber = this.eventManager.subscribe('gIDIdentityListModification', () => this.reset());
  }

  delete(gIDIdentity: IGIDIdentity): void {
    const modalRef = this.modalService.open(GIDIdentityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gIDIdentity = gIDIdentity;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGIDIdentities(data: IGIDIdentity[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.gIDIdentities.push(data[i]);
      }
    }
  }
}
