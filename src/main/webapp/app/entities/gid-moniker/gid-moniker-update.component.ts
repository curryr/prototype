import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGIDMoniker, GIDMoniker } from 'app/shared/model/gid-moniker.model';
import { GIDMonikerService } from './gid-moniker.service';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from 'app/entities/gid-identity/gid-identity.service';
import { IGIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from 'app/entities/gid-user/gid-user.service';
import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { GIDMembershipService } from 'app/entities/gid-membership/gid-membership.service';

type SelectableEntity = IGIDIdentity | IGIDUser | IGIDMembership;

@Component({
  selector: 'jhi-gid-moniker-update',
  templateUrl: './gid-moniker-update.component.html'
})
export class GIDMonikerUpdateComponent implements OnInit {
  isSaving = false;
  gididentities: IGIDIdentity[] = [];
  gidusers: IGIDUser[] = [];
  gidmemberships: IGIDMembership[] = [];

  editForm = this.fb.group({
    id: [],
    moniker: [],
    prefix: [],
    gIDIdentity: [],
    gIDIdentity: [],
    user: [],
    user: [],
    membership: []
  });

  constructor(
    protected gIDMonikerService: GIDMonikerService,
    protected gIDIdentityService: GIDIdentityService,
    protected gIDUserService: GIDUserService,
    protected gIDMembershipService: GIDMembershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMoniker }) => {
      this.updateForm(gIDMoniker);

      this.gIDIdentityService.query().subscribe((res: HttpResponse<IGIDIdentity[]>) => (this.gididentities = res.body || []));

      this.gIDUserService.query().subscribe((res: HttpResponse<IGIDUser[]>) => (this.gidusers = res.body || []));

      this.gIDMembershipService.query().subscribe((res: HttpResponse<IGIDMembership[]>) => (this.gidmemberships = res.body || []));
    });
  }

  updateForm(gIDMoniker: IGIDMoniker): void {
    this.editForm.patchValue({
      id: gIDMoniker.id,
      moniker: gIDMoniker.moniker,
      prefix: gIDMoniker.prefix,
      gIDIdentity: gIDMoniker.gIDIdentity,
      gIDIdentity: gIDMoniker.gIDIdentity,
      user: gIDMoniker.user,
      user: gIDMoniker.user,
      membership: gIDMoniker.membership
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gIDMoniker = this.createFromForm();
    if (gIDMoniker.id !== undefined) {
      this.subscribeToSaveResponse(this.gIDMonikerService.update(gIDMoniker));
    } else {
      this.subscribeToSaveResponse(this.gIDMonikerService.create(gIDMoniker));
    }
  }

  private createFromForm(): IGIDMoniker {
    return {
      ...new GIDMoniker(),
      id: this.editForm.get(['id'])!.value,
      moniker: this.editForm.get(['moniker'])!.value,
      prefix: this.editForm.get(['prefix'])!.value,
      gIDIdentity: this.editForm.get(['gIDIdentity'])!.value,
      gIDIdentity: this.editForm.get(['gIDIdentity'])!.value,
      user: this.editForm.get(['user'])!.value,
      user: this.editForm.get(['user'])!.value,
      membership: this.editForm.get(['membership'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGIDMoniker>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
