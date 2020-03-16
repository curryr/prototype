import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';

@Component({
  selector: 'jhi-gid-moniker-detail',
  templateUrl: './gid-moniker-detail.component.html'
})
export class GIDMonikerDetailComponent implements OnInit {
  gIDMoniker: IGIDMoniker | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMoniker }) => (this.gIDMoniker = gIDMoniker));
  }

  previousState(): void {
    window.history.back();
  }
}
