import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGIDMembership } from 'app/shared/model/gid-membership.model';

@Component({
  selector: 'jhi-gid-membership-detail',
  templateUrl: './gid-membership-detail.component.html'
})
export class GIDMembershipDetailComponent implements OnInit {
  gIDMembership: IGIDMembership | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMembership }) => (this.gIDMembership = gIDMembership));
  }

  previousState(): void {
    window.history.back();
  }
}
