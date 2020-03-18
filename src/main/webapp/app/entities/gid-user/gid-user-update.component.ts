import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IGIDUser, GIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from './gid-user.service';
import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerSetService } from 'app/entities/gid-moniker-set/gid-moniker-set.service';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from 'app/entities/gid-identity/gid-identity.service';

type SelectableEntity = IGIDMonikerSet | IGIDIdentity;

@Component({
  selector: 'jhi-gid-user-update',
  templateUrl: './gid-user-update.component.html'
})
export class GIDUserUpdateComponent implements OnInit {
  isSaving = false;
  monikers: IGIDMonikerSet[] = [];
  gididentities: IGIDIdentity[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    monikers: [],
    identities: []
  });

  constructor(
    protected gIDUserService: GIDUserService,
    protected gIDMonikerSetService: GIDMonikerSetService,
    protected gIDIdentityService: GIDIdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDUser }) => {
      this.updateForm(gIDUser);

      this.gIDMonikerSetService
        .query({ filter: 'giduser-is-null' })
        .pipe(
          map((res: HttpResponse<IGIDMonikerSet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGIDMonikerSet[]) => {
          if (!gIDUser.monikers || !gIDUser.monikers.id) {
            this.monikers = resBody;
          } else {
            this.gIDMonikerSetService
              .find(gIDUser.monikers.id)
              .pipe(
                map((subRes: HttpResponse<IGIDMonikerSet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGIDMonikerSet[]) => (this.monikers = concatRes));
          }
        });

      this.gIDIdentityService.query().subscribe((res: HttpResponse<IGIDIdentity[]>) => (this.gididentities = res.body || []));
    });
  }

  updateForm(gIDUser: IGIDUser): void {
    this.editForm.patchValue({
      id: gIDUser.id,
      firstName: gIDUser.firstName,
      lastName: gIDUser.lastName,
      monikers: gIDUser.monikers,
      identities: gIDUser.identities
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gIDUser = this.createFromForm();
    if (gIDUser.id !== undefined) {
      this.subscribeToSaveResponse(this.gIDUserService.update(gIDUser));
    } else {
      this.subscribeToSaveResponse(this.gIDUserService.create(gIDUser));
    }
  }

  private createFromForm(): IGIDUser {
    return {
      ...new GIDUser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      monikers: this.editForm.get(['monikers'])!.value,
      identities: this.editForm.get(['identities'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGIDUser>>): void {
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

  getSelected(selectedVals: IGIDIdentity[], option: IGIDIdentity): IGIDIdentity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
