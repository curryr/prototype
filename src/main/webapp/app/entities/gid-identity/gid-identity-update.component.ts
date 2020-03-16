import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGIDIdentity, GIDIdentity } from 'app/shared/model/gid-identity.model';
import { GIDIdentityService } from './gid-identity.service';
import { IGIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from 'app/entities/gid-user/gid-user.service';

@Component({
  selector: 'jhi-gid-identity-update',
  templateUrl: './gid-identity-update.component.html'
})
export class GIDIdentityUpdateComponent implements OnInit {
  isSaving = false;
  gidusers: IGIDUser[] = [];

  editForm = this.fb.group({
    id: [],
    gid: [],
    pgid: [],
    user: []
  });

  constructor(
    protected gIDIdentityService: GIDIdentityService,
    protected gIDUserService: GIDUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDIdentity }) => {
      this.updateForm(gIDIdentity);

      this.gIDUserService.query().subscribe((res: HttpResponse<IGIDUser[]>) => (this.gidusers = res.body || []));
    });
  }

  updateForm(gIDIdentity: IGIDIdentity): void {
    this.editForm.patchValue({
      id: gIDIdentity.id,
      gid: gIDIdentity.gid,
      pgid: gIDIdentity.pgid,
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

  trackById(index: number, item: IGIDUser): any {
    return item.id;
  }
}
