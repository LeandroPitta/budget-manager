import { Component } from '@angular/core';
import { NavigationService } from '../../core/navigation.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private navigationService: NavigationService
  ) {}

  login(): void {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('backgroundColor', response.backgroundColor);
        localStorage.setItem('title', response.title);
        localStorage.setItem('titleColor', response.titleColor);
        localStorage.setItem('fontFamily', response.fontFamily);
        localStorage.setItem('budgetGif', response.budgetGif);
        localStorage.setItem('backgroundGif', response.backgroundGif);

        this.navigationService.navigateTo('dashboard');
      },
      (error) => {
        if (error.status === 403) {
          this.errorMessage = 'Incorrect username or password';
        } else {
          this.errorMessage = 'An error occurred. Please try again.';
        }
      }
    );
  }

  navigateToRegister(): void {
    this.navigationService.navigateTo('register');
  }
}
