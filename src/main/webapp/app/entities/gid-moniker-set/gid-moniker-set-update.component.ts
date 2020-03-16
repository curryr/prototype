import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGIDMonikerSet, GIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';
import { GIDMonikerSetService } from './gid-moniker-set.service';
import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';
import { GIDMonikerService } from 'app/entities/gid-moniker/gid-moniker.service';

@Component({
  selector: 'jhi-gid-moniker-set-update',
  templateUrl: './gid-moniker-set-update.component.html'
})
export class GIDMonikerSetUpdateComponent implements OnInit {
  isSaving = false;
  gidmonikers: IGIDMoniker[] = [];

  editForm = this.fb.group({
    id: [],
    gIDMonikers: []
  });

  constructor(
    protected gIDMonikerSetService: GIDMonikerSetService,
    protected gIDMonikerService: GIDMonikerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gIDMonikerSet }) => {
      this.updateForm(gIDMonikerSet);

      this.gIDMonikerService.query().subscribe((res: HttpResponse<IGIDMoniker[]>) => (this.gidmonikers = res.body || []));
    });
  }

  updateForm(gIDMonikerSet: IGIDMonikerSet): void {
    this.editForm.patchValue({
      id: gIDMonikerSet.id,
      gIDMonikers: gIDMonikerSet.gIDMonikers
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gIDMonikerSet = this.createFromForm();
    if (gIDMonikerSet.id !== undefined) {
      this.subscribeToSaveResponse(this.gIDMonikerSetService.update(gIDMonikerSet));
    } else {
      this.subscribeToSaveResponse(this.gIDMonikerSetService.create(gIDMonikerSet));
    }
  }

  private createFromForm(): IGIDMonikerSet {
    return {
      ...new GIDMonikerSet(),
      id: this.editForm.get(['id'])!.value,
      gIDMonikers: this.editForm.get(['gIDMonikers'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGIDMonikerSet>>): void {
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

  trackById(index: number, item: IGIDMoniker): any {
    return item.id;
  }

  getSelected(selectedVals: IGIDMoniker[], option: IGIDMoniker): IGIDMoniker {
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
