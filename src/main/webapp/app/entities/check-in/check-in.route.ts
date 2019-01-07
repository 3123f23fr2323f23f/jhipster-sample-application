import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CheckIn } from 'app/shared/model/check-in.model';
import { CheckInService } from './check-in.service';
import { CheckInComponent } from './check-in.component';
import { CheckInDetailComponent } from './check-in-detail.component';
import { CheckInUpdateComponent } from './check-in-update.component';
import { CheckInDeletePopupComponent } from './check-in-delete-dialog.component';
import { ICheckIn } from 'app/shared/model/check-in.model';

@Injectable({ providedIn: 'root' })
export class CheckInResolve implements Resolve<ICheckIn> {
    constructor(private service: CheckInService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CheckIn> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CheckIn>) => response.ok),
                map((checkIn: HttpResponse<CheckIn>) => checkIn.body)
            );
        }
        return of(new CheckIn());
    }
}

export const checkInRoute: Routes = [
    {
        path: 'check-in',
        component: CheckInComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CheckIns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'check-in/:id/view',
        component: CheckInDetailComponent,
        resolve: {
            checkIn: CheckInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckIns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'check-in/new',
        component: CheckInUpdateComponent,
        resolve: {
            checkIn: CheckInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckIns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'check-in/:id/edit',
        component: CheckInUpdateComponent,
        resolve: {
            checkIn: CheckInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckIns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const checkInPopupRoute: Routes = [
    {
        path: 'check-in/:id/delete',
        component: CheckInDeletePopupComponent,
        resolve: {
            checkIn: CheckInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckIns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
