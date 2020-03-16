import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGIDIdentity } from 'app/shared/model/gid-identity.model';

@Component({
  selector: 'jhi-gid-identity-detail',
  templateUrl: './gid-identity-detail.component.html'
})
export class GIDIdentityDetailComponent implements OnInit {
  gIDIdentity: IGIDIdentity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDIdentity }) => (this.gIDIdentity = gIDIdentity));
  }

  previousState(): void {
    window.history.back();
  }
}
