import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICheckIn } from 'app/shared/model/check-in.model';
import { CheckInService } from './check-in.service';

@Component({
    selector: 'jhi-check-in-update',
    templateUrl: './check-in-update.component.html'
})
export class CheckInUpdateComponent implements OnInit {
    checkIn: ICheckIn;
    isSaving: boolean;

    constructor(protected checkInService: CheckInService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ checkIn }) => {
            this.checkIn = checkIn;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.checkIn.id !== undefined) {
            this.subscribeToSaveResponse(this.checkInService.update(this.checkIn));
        } else {
            this.subscribeToSaveResponse(this.checkInService.create(this.checkIn));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckIn>>) {
        result.subscribe((res: HttpResponse<ICheckIn>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
