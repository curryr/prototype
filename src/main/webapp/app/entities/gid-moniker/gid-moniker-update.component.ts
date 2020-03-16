import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGIDMoniker, GIDMoniker } from 'app/shared/model/gid-moniker.model';
import { GIDMonikerService } from './gid-moniker.service';

@Component({
  selector: 'jhi-gid-moniker-update',
  templateUrl: './gid-moniker-update.component.html'
})
export class GIDMonikerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    moniker: [],
    prefix: []
  });

  constructor(protected gIDMonikerService: GIDMonikerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMoniker }) => {
      this.updateForm(gIDMoniker);
    });
  }

  updateForm(gIDMoniker: IGIDMoniker): void {
    this.editForm.patchValue({
      id: gIDMoniker.id,
      moniker: gIDMoniker.moniker,
      prefix: gIDMoniker.prefix
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
      prefix: this.editForm.get(['prefix'])!.value
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
}
