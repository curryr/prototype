import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IGIDIdentity, GIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from './gid-identity.service';
import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerSetService } from 'app/entities/gid-moniker-set/gid-moniker-set.service';
import { IGIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from 'app/entities/gid-user/gid-user.service';

type SelectableEntity = IGIDMonikerSet | IGIDUser;

@Component({
  selector: 'jhi-gid-identity-update',
  templateUrl: './gid-identity-update.component.html'
})
export class GIDIdentityUpdateComponent implements OnInit {
  isSaving = false;
  monikers: IGIDMonikerSet[] = [];
  fullmonikersets: IGIDMonikerSet[] = [];
  standardmonikersets: IGIDMonikerSet[] = [];
  gidusers: IGIDUser[] = [];

  editForm = this.fb.group({
    id: [],
    gid: [],
    pgid: [],
    monikers: [],
    fullMonikerSet: [],
    standardMonikerSet: [],
    user: []
  });

  constructor(
    protected gIDIdentityService: GIDIdentityService,
    protected gIDMonikerSetService: GIDMonikerSetService,
    protected gIDUserService: GIDUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDIdentity }) => {
      this.updateForm(gIDIdentity);

      this.gIDMonikerSetService
        .query({ filter: 'gididentity-is-null' })
        .pipe(
          map((res: HttpResponse<IGIDMonikerSet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGIDMonikerSet[]) => {
          if (!gIDIdentity.monikers || !gIDIdentity.monikers.id) {
            this.monikers = resBody;
          } else {
            this.gIDMonikerSetService
              .find(gIDIdentity.monikers.id)
              .pipe(
                map((subRes: HttpResponse<IGIDMonikerSet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGIDMonikerSet[]) => (this.monikers = concatRes));
          }
        });

      this.gIDMonikerSetService
        .query({ filter: 'gididentity-is-null' })
        .pipe(
          map((res: HttpResponse<IGIDMonikerSet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGIDMonikerSet[]) => {
          if (!gIDIdentity.fullMonikerSet || !gIDIdentity.fullMonikerSet.id) {
            this.fullmonikersets = resBody;
          } else {
            this.gIDMonikerSetService
              .find(gIDIdentity.fullMonikerSet.id)
              .pipe(
                map((subRes: HttpResponse<IGIDMonikerSet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGIDMonikerSet[]) => (this.fullmonikersets = concatRes));
          }
        });

      this.gIDMonikerSetService
        .query({ filter: 'gididentity-is-null' })
        .pipe(
          map((res: HttpResponse<IGIDMonikerSet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGIDMonikerSet[]) => {
          if (!gIDIdentity.standardMonikerSet || !gIDIdentity.standardMonikerSet.id) {
            this.standardmonikersets = resBody;
          } else {
            this.gIDMonikerSetService
              .find(gIDIdentity.standardMonikerSet.id)
              .pipe(
                map((subRes: HttpResponse<IGIDMonikerSet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGIDMonikerSet[]) => (this.standardmonikersets = concatRes));
          }
        });

      this.gIDUserService.query().subscribe((res: HttpResponse<IGIDUser[]>) => (this.gidusers = res.body || []));
    });
  }

  updateForm(gIDIdentity: IGIDIdentity): void {
    this.editForm.patchValue({
      id: gIDIdentity.id,
      gid: gIDIdentity.gid,
      pgid: gIDIdentity.pgid,
      monikers: gIDIdentity.monikers,
      fullMonikerSet: gIDIdentity.fullMonikerSet,
      standardMonikerSet: gIDIdentity.standardMonikerSet,
      user: gIDIdentity.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gIDIdentity = this.createFromForm();
    if (gIDIdentity.id !== undefined) {
      this.subscribeToSaveResponse(this.gIDIdentityService.update(gIDIdentity));
    } else {
      this.subscribeToSaveResponse(this.gIDIdentityService.create(gIDIdentity));
    }
  }

  private createFromForm(): IGIDIdentity {
    return {
      ...new GIDIdentity(),
      id: this.editForm.get(['id'])!.value,
      gid: this.editForm.get(['gid'])!.value,
      pgid: this.editForm.get(['pgid'])!.value,
      monikers: this.editForm.get(['monikers'])!.value,
      fullMonikerSet: this.editForm.get(['fullMonikerSet'])!.value,
      standardMonikerSet: this.editForm.get(['standardMonikerSet'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGIDIdentity>>): void {
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
