import { Injectable } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class NavigationService {
  private currentView: string = 'login';

  constructor(private authService: AuthService) {
    this.checkAuthentication();
  }

  getCurrentView(): string {
    return this.currentView;
  }

  navigateTo(view: string): void {
    this.currentView = view;
  }

  private checkAuthentication(): void {
    if (this.authService.isAuthenticated()) {
      this.currentView = 'dashboard';
    } else {
      this.currentView = 'login';
    }
  }
}
