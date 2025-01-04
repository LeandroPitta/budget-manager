import { Component, HostListener, OnInit } from '@angular/core';
import { NavigationService } from '../../core/navigation.service';
import { CostService } from '../../services/cost.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  costs: any[] = [];
  giftData: any = { gift: 0, spent: 0, available: 0 };
  showCostForm: boolean = false;
  showCostUpdateForm: boolean = false;
  selectedCost: any = null;
  showBackToTop: boolean = false;
  private inactivityTimer: any;
  title: string = '';
  backgroundGif: string = '';
  isBackgroundGifNotNull: boolean = true;
  budgetGif: string = '';
  isBudgetGifUrl: boolean = false;

  constructor(
    private costService: CostService,
    private navigationService: NavigationService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.costService.getCosts(token).subscribe((response) => {
        this.costs = response.costs;
      });

      this.costService.getGiftData(token).subscribe((response) => {
        this.giftData = response;
      });
    }
    this.resetInactivityTimer();

    this.loadCustomization();
  }

  loadCustomization(): void {
    if (localStorage.getItem('title') === 'null') {
      this.title = '';
    } else {
      this.title = localStorage.getItem('title') || '';
    }

    const backgroundColor = localStorage.getItem('backgroundColor');
    const titleColor = localStorage.getItem('titleColor');
    const fontFamily = localStorage.getItem('fontFamily');

    if (backgroundColor) {
      document.documentElement.style.setProperty(
        '--background-gradient',
        backgroundColor
      );
    }
    if (titleColor) {
      document.documentElement.style.setProperty(
        '--font-color-title',
        titleColor
      );
    }
    if (fontFamily) {
      document.documentElement.style.setProperty('--font-family', fontFamily);
    }

    this.backgroundGif = localStorage.getItem('backgroundGif') || '';
    this.isBackgroundGifNotNull = this.backgroundGif !== 'null';

    this.budgetGif = localStorage.getItem('budgetGif') || '';
    this.isBudgetGifUrl = this.isValidUrl(this.budgetGif);
  }

  isValidUrl(string: string): boolean {
    try {
      new URL(string);
      return true;
    } catch (_) {
      return false;
    }
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

  updateCost(cost: any): void {
    this.selectedCost = cost;
  }

  saveUpdatedCost(updatedCost: any): void {
    const index = this.costs.findIndex((cost) => cost.id === updatedCost.id);
    if (index !== -1) {
      this.costs[index] = updatedCost;
    }
    this.selectedCost = null;
    const token = localStorage.getItem('token');
    if (token) {
      this.costService.getGiftData(token).subscribe((response) => {
        this.giftData = response;
      });
    }
  }

  cancelUpdateCostForm(): void {
    this.selectedCost = null;
  }

  deleteCost(cost: any): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.costService.deleteCost(cost.id, token).subscribe(() => {
        this.costs = this.costs.filter((c) => c.id !== cost.id);
      });
      this.costService.getGiftData(token).subscribe((response) => {
        this.giftData = response;
      });
    }
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
    localStorage.clear();
    this.navigationService.navigateTo('login');
  }
}
