import { Component, OnInit } from '@angular/core';
import { CostService } from '../services/cost.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  costs: any[] = [];
  giftData: any = { gift: 0, spent: 0, available: 0 };
  showCostForm: boolean = false; // Flag to control the display of the cost form

  constructor(private costService: CostService) { }

  ngOnInit(): void {
    this.costService.getCosts().subscribe(response => {
      this.costs = response.costs;
    });

    this.costService.getGiftData().subscribe(response => {
      this.giftData = response;
    });
  }

  toggleCostForm(): void {
    this.showCostForm = !this.showCostForm;
  }

  addCost(cost: any): void {
    this.costService.addCost(cost).subscribe(() => {
      this.costs.push(cost);
      this.showCostForm = false;
    });
  }

  cancelCostForm(): void {
    this.showCostForm = false;
  }
}