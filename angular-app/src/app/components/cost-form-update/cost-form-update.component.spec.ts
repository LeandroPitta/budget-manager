import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CostFormUpdateComponent } from './cost-form-update.component';

describe('CostFormUpdateComponent', () => {
  let component: CostFormUpdateComponent;
  let fixture: ComponentFixture<CostFormUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CostFormUpdateComponent]
    });
    fixture = TestBed.createComponent(CostFormUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
