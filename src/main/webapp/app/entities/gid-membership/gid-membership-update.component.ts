import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IGIDMembership, GIDMembership } from 'app/shared/model/gid-membership.model';
import { GIDMembershipService } from './gid-membership.service';
import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerSetService } from 'app/entities/gid-moniker-set/gid-moniker-set.service';

@Component({
  selector: 'jhi-gid-membership-update',
  templateUrl: './gid-membership-update.component.html'
})
export class GIDMembershipUpdateComponent implements OnInit {
  isSaving = false;
  monikers: IGIDMonikerSet[] = [];

  editForm = this.fb.group({
    id: [],
    ogid: [],
    tenantKey: [],
    tenantUserKey: [],
    tenantUserBlock: [],
    monikers: []
  });

  constructor(
    protected gIDMembershipService: GIDMembershipService,
    protected gIDMonikerSetService: GIDMonikerSetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMembership }) => {
      this.updateForm(gIDMembership);

      this.gIDMonikerSetService
        .query({ filter: 'gidmembership-is-null' })
        .pipe(
          map((res: HttpResponse<IGIDMonikerSet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGIDMonikerSet[]) => {
          if (!gIDMembership.monikers || !gIDMembership.monikers.id) {
            this.monikers = resBody;
          } else {
            this.gIDMonikerSetService
              .find(gIDMembership.monikers.id)
              .pipe(
                map((subRes: HttpResponse<IGIDMonikerSet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGIDMonikerSet[]) => (this.monikers = concatRes));
          }
        });
    });
  }

  updateForm(gIDMembership: IGIDMembership): void {
    this.editForm.patchValue({
      id: gIDMembership.id,
      ogid: gIDMembership.ogid,
      tenantKey: gIDMembership.tenantKey,
      tenantUserKey: gIDMembership.tenantUserKey,
      tenantUserBlock: gIDMembership.tenantUserBlock,
      monikers: gIDMembership.monikers
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
      monikers: this.editForm.get(['monikers'])!.value
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

  trackById(index: number, item: IGIDMonikerSet): any {
    return item.id;
  }
}
