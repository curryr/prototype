import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';

@Component({
  selector: 'jhi-gid-moniker-set-detail',
  templateUrl: './gid-moniker-set-detail.component.html'
})
export class GIDMonikerSetDetailComponent implements OnInit {
  gIDMonikerSet: IGIDMonikerSet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMonikerSet }) => (this.gIDMonikerSet = gIDMonikerSet));
  }

  previousState(): void {
    window.history.back();
  }
}
