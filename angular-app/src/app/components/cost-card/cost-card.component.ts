import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-cost-card',
  templateUrl: './cost-card.component.html',
  styleUrls: ['./cost-card.component.css'],
})
export class CostCardComponent {
  @Input() cost: any;
  @Output() edit = new EventEmitter<void>();
  @Output() delete = new EventEmitter<void>();

  editCost(): void {
    this.edit.emit();
  }

  deleteCost(): void {
    this.delete.emit();
  }
}
