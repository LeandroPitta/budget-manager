import { Component, HostListener, OnInit } from '@angular/core';
import { CostService } from '../services/cost.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  costs: any[] = [];
  giftData: any = { gift: 0, spent: 0, available: 0 };
  showCostForm: boolean = false;
  showBackToTop: boolean = false;

  constructor(private costService: CostService) { }

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.costService.getCosts(token).subscribe(response => {
        this.costs = response.costs;
      });

      this.costService.getGiftData(token).subscribe(response => {
        this.giftData = response;
      });
    }
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

  scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    this.showBackToTop = window.scrollY > 1;
  }
}