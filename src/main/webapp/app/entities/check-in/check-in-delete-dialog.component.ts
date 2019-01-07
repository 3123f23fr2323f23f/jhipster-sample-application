import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckIn } from 'app/shared/model/check-in.model';
import { CheckInService } from './check-in.service';

@Component({
    selector: 'jhi-check-in-delete-dialog',
    templateUrl: './check-in-delete-dialog.component.html'
})
export class CheckInDeleteDialogComponent {
    checkIn: ICheckIn;

    constructor(protected checkInService: CheckInService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.checkInService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'checkInListModification',
                content: 'Deleted an checkIn'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-check-in-delete-popup',
    template: ''
})
export class CheckInDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ checkIn }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CheckInDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.checkIn = checkIn;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
