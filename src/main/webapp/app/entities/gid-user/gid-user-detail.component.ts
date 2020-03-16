import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGIDUser } from 'app/shared/model/gid-user.model';

@Component({
  selector: 'jhi-gid-user-detail',
  templateUrl: './gid-user-detail.component.html'
})
export class GIDUserDetailComponent implements OnInit {
  gIDUser: IGIDUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDUser }) => (this.gIDUser = gIDUser));
  }

  previousState(): void {
    window.history.back();
  }
}
