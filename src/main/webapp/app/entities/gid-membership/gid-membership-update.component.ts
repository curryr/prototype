import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGIDMembership, GIDMembership } from 'app/shared/model/gid-membership.model';
import { GIDMembershipService } from './gid-membership.service';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from 'app/entities/gid-identity/gid-identity.service';

@Component({
  selector: 'jhi-gid-membership-update',
  templateUrl: './gid-membership-update.component.html'
})
export class GIDMembershipUpdateComponent implements OnInit {
  isSaving = false;
  gididentities: IGIDIdentity[] = [];

  editForm = this.fb.group({
    id: [],
    ogid: [],
    tenantKey: [],
    tenantUserKey: [],
    tenantUserBlock: [],
    identity: []
  });

  constructor(
    protected gIDMembershipService: GIDMembershipService,
    protected gIDIdentityService: GIDIdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMembership }) => {
      this.updateForm(gIDMembership);

      this.gIDIdentityService.query().subscribe((res: HttpResponse<IGIDIdentity[]>) => (this.gididentities = res.body || []));
    });
  }

  updateForm(gIDMembership: IGIDMembership): void {
    this.editForm.patchValue({
      id: gIDMembership.id,
      ogid: gIDMembership.ogid,
      tenantKey: gIDMembership.tenantKey,
      tenantUserKey: gIDMembership.tenantUserKey,
      tenantUserBlock: gIDMembership.tenantUserBlock,
      identity: gIDMembership.identity
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gIDMembership = this.createFromForm();
    if (gIDMembership.id !== undefined) {
      this.subscribeToSaveResponse(this.gIDMembershipService.update(gIDMembership));
    } else {
      this.subscribeToSaveResponse(this.gIDMembershipService.create(gIDMembership));
    }
  }

  private createFromForm(): IGIDMembership {
    return {
      ...new GIDMembership(),
      id: this.editForm.get(['id'])!.value,
      ogid: this.editForm.get(['ogid'])!.value,
      tenantKey: this.editForm.get(['tenantKey'])!.value,
      tenantUserKey: this.editForm.get(['tenantUserKey'])!.value,
      tenantUserBlock: this.editForm.get(['tenantUserBlock'])!.value,
      identity: this.editForm.get(['identity'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGIDMembership>>): void {
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

  trackById(index: number, item: IGIDIdentity): any {
    return item.id;
  }
}
