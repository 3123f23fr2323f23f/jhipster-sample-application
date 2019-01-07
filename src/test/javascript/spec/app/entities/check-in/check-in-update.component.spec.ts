/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CheckInUpdateComponent } from 'app/entities/check-in/check-in-update.component';
import { CheckInService } from 'app/entities/check-in/check-in.service';
import { CheckIn } from 'app/shared/model/check-in.model';

describe('Component Tests', () => {
    describe('CheckIn Management Update Component', () => {
        let comp: CheckInUpdateComponent;
        let fixture: ComponentFixture<CheckInUpdateComponent>;
        let service: CheckInService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CheckInUpdateComponent]
            })
                .overrideTemplate(CheckInUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CheckInUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CheckInService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CheckIn(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.checkIn = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CheckIn();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.checkIn = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
