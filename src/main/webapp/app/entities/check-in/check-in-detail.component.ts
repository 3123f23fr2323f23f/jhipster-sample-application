import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckIn } from 'app/shared/model/check-in.model';

@Component({
    selector: 'jhi-check-in-detail',
    templateUrl: './check-in-detail.component.html'
})
export class CheckInDetailComponent implements OnInit {
    checkIn: ICheckIn;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ checkIn }) => {
            this.checkIn = checkIn;
        });
    }

    previousState() {
        window.history.back();
    }
}
