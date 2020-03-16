import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGIDUser, GIDUser } from 'app/shared/model/gid-user.model';
import { GIDUserService } from './gid-user.service';

@Component({
  selector: 'jhi-gid-user-update',
  templateUrl: './gid-user-update.component.html'
})
export class GIDUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: []
  });

  constructor(protected gIDUserService: GIDUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDUser }) => {
      this.updateForm(gIDUser);
    });
  }

  updateForm(gIDUser: IGIDUser): void {
    this.editForm.patchValue({
      id: gIDUser.id,
      firstName: gIDUser.firstName,
      lastName: gIDUser.lastName
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
      lastName: this.editForm.get(['lastName'])!.value
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
}
