import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CostService } from '../services/cost.service';

@Component({
  selector: 'app-cost-form',
  templateUrl: './cost-form.component.html',
  styleUrls: ['./cost-form.component.css']
})
export class CostFormComponent {
  @Input() available: number = 0;
  @Output() save = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<void>();
  cost = { buy: '', cost: 0 };
  errorMessage: string = '';

  constructor(private costService: CostService, private snackBar: MatSnackBar) { }

  onSave(): void {
    // Validation for the Buy field
    if (this.cost.buy.length < 3) {
      this.errorMessage = 'Buy must be at least 3 characters long.';
      this.snackBar.open(this.errorMessage, 'Close', {
        duration: 3000,
        verticalPosition: 'top', // Set position to top
      });
      return;
    }

    // Validation for the Cost field
    if (this.cost.cost < 0.01 || this.cost.cost > this.available) {
      this.errorMessage = `Cost must be between $0.01 and $${this.available}.`;
      this.snackBar.open(this.errorMessage, 'Close', {
        duration: 3000,
        verticalPosition: 'top', // Set position to top
      });
      return;
    }

    this.costService.addCost(this.cost).subscribe(() => {
      window.location.reload();
    });
  }

  onCancel(): void {
    this.cancel.emit();
  }
}
