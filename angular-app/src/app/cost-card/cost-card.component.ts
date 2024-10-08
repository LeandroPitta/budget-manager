import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-cost-card',
  templateUrl: './cost-card.component.html',
  styleUrls: ['./cost-card.component.css']
})
export class CostCardComponent {
  @Input() cost: any;
}