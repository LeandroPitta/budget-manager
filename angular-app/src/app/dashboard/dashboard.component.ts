import { Component, HostListener, OnInit } from '@angular/core';
import { NavigationService } from '../core/navigation.service';
import { CostService } from '../services/cost.service';
import { AuthService } from '../services/auth.service';

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
  private inactivityTimer: any;

  constructor(private costService: CostService,
    private navigationService: NavigationService,
    private authService: AuthService
  ) { }

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
    this.resetInactivityTimer();
  }

  @HostListener('document:mousemove', [])
  @HostListener('document:keydown', [])
  resetInactivityTimer(): void {
    clearTimeout(this.inactivityTimer);
    this.inactivityTimer = setTimeout(() => this.logout(), 5 * 60 * 1000);
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

  openSettings() {
    // Lógica para abrir as configurações
  }

  logout() {
    this.authService.logout();
    this.navigationService.navigateTo('login');
  }
}