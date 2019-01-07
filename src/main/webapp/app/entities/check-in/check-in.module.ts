import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    CheckInComponent,
    CheckInDetailComponent,
    CheckInUpdateComponent,
    CheckInDeletePopupComponent,
    CheckInDeleteDialogComponent,
    checkInRoute,
    checkInPopupRoute
} from './';

const ENTITY_STATES = [...checkInRoute, ...checkInPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CheckInComponent,
        CheckInDetailComponent,
        CheckInUpdateComponent,
        CheckInDeleteDialogComponent,
        CheckInDeletePopupComponent
    ],
    entryComponents: [CheckInComponent, CheckInUpdateComponent, CheckInDeleteDialogComponent, CheckInDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationCheckInModule {}
