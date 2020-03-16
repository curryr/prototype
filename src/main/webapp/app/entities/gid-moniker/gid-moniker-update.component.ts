import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGIDMoniker, GIDMoniker } from 'app/shared/model/gid-moniker.model';
import { GIDMonikerService } from './gid-moniker.service';
import { IGIDMembership } from 'app/shared/model/gid-membership.model';
import { GIDMembershipService } from 'app/entities/gid-membership/gid-membership.service';
import { IGIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from 'app/entities/gid-user/gid-user.service';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from 'app/entities/gid-identity/gid-identity.service';

type SelectableEntity = IGIDMembership | IGIDUser | IGIDIdentity;

@Component({
  selector: 'jhi-gid-moniker-update',
  templateUrl: './gid-moniker-update.component.html'
})
export class GIDMonikerUpdateComponent implements OnInit {
  isSaving = false;
  gidmemberships: IGIDMembership[] = [];
  gidusers: IGIDUser[] = [];
  gididentities: IGIDIdentity[] = [];

  editForm = this.fb.group({
    id: [],
    moniker: [],
    prefix: [],
    userOf: [],
    userOf: [],
    userOf: [],
    contains: [],
    contains: []
  });

  constructor(
    protected gIDMonikerService: GIDMonikerService,
    protected gIDMembershipService: GIDMembershipService,
    protected gIDUserService: GIDUserService,
    protected gIDIdentityService: GIDIdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMoniker }) => {
      this.updateForm(gIDMoniker);

      this.gIDMembershipService.query().subscribe((res: HttpResponse<IGIDMembership[]>) => (this.gidmemberships = res.body || []));

      this.gIDUserService.query().subscribe((res: HttpResponse<IGIDUser[]>) => (this.gidusers = res.body || []));

      this.gIDIdentityService.query().subscribe((res: HttpResponse<IGIDIdentity[]>) => (this.gididentities = res.body || []));
    });
  }

  updateForm(gIDMoniker: IGIDMoniker): void {
    this.editForm.patchValue({
      id: gIDMoniker.id,
      moniker: gIDMoniker.moniker,
      prefix: gIDMoniker.prefix,
      userOf: gIDMoniker.userOf,
      userOf: gIDMoniker.userOf,
      userOf: gIDMoniker.userOf,
      contains: gIDMoniker.contains,
      contains: gIDMoniker.contains
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
      userOf: this.editForm.get(['userOf'])!.value,
      userOf: this.editForm.get(['userOf'])!.value,
      userOf: this.editForm.get(['userOf'])!.value,
      contains: this.editForm.get(['contains'])!.value,
      contains: this.editForm.get(['contains'])!.value
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
