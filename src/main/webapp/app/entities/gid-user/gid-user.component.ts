import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGIDUser } from 'app/shared/model/gid-user.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GIDUserService } from './gid-user.service';
import { GIDUserDeleteDialogComponent } from './gid-user-delete-dialog.component';

@Component({
  selector: 'jhi-gid-user',
  templateUrl: './gid-user.component.html'
})
export class GIDUserComponent implements OnInit, OnDestroy {
  gIDUsers: IGIDUser[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected gIDUserService: GIDUserService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gIDUsers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.gIDUserService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGIDUser[]>) => this.paginateGIDUsers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.gIDUsers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGIDUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGIDUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGIDUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('gIDUserListModification', () => this.reset());
  }

  delete(gIDUser: IGIDUser): void {
    const modalRef = this.modalService.open(GIDUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gIDUser = gIDUser;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGIDUsers(data: IGIDUser[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.gIDUsers.push(data[i]);
      }
    }
  }
}
