/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CheckInDetailComponent } from 'app/entities/check-in/check-in-detail.component';
import { CheckIn } from 'app/shared/model/check-in.model';

describe('Component Tests', () => {
    describe('CheckIn Management Detail Component', () => {
        let comp: CheckInDetailComponent;
        let fixture: ComponentFixture<CheckInDetailComponent>;
        const route = ({ data: of({ checkIn: new CheckIn(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CheckInDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CheckInDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CheckInDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.checkIn).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
