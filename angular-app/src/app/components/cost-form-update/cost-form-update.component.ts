import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CostService } from '../../services/cost.service';

@Component({
  selector: 'app-cost-form-update',
  templateUrl: './cost-form-update.component.html',
  styleUrls: ['./cost-form-update.component.css']
})
export class CostFormUpdateComponent {
  @Input() available: number = 0;
  @Input() cost: any = { buy: '', cost: 0 };
  @Output() save = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<void>();
  errorMessage: string = '';

  constructor(
    private costService: CostService,
    private snackBar: MatSnackBar
  ) { }

  onSave(): void {
    if (this.cost.buy.length < 3) {
      this.errorMessage = 'Buy must be at least 3 characters long.';
      this.snackBar.open(this.errorMessage, 'Close', {
        duration: 3000,
        verticalPosition: 'top',
      });
      return;
    }

    if (this.cost.cost < 0.01 || this.cost.cost > this.available) {
      this.errorMessage = `Cost must be between $0.01 and $${this.available}.`;
      this.snackBar.open(this.errorMessage, 'Close', {
        duration: 3000,
        verticalPosition: 'top',
      });
      return;
    }

    this.costService.updateCost(this.cost.id, this.cost.buy, this.cost.cost).subscribe(() => {
      this.save.emit(this.cost);
    });
  }

  onCancel(): void {
    this.cancel.emit();
  }
}