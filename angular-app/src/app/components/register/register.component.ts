import { Component } from '@angular/core';
import { NavigationService } from '../../core/navigation.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private navigationService: NavigationService,
    private snackBar: MatSnackBar
  ) { }

  register(): void {
    this.authService.register(this.username, this.password).subscribe(
      (response) => {
        this.authService.login(this.username, this.password).subscribe(
          (loginResponse) => {
            localStorage.setItem('token', loginResponse.token);
            this.snackBar.open('User successfully registered', 'Close', {
              duration: 3000,
              verticalPosition: 'top',
            });
            this.navigationService.navigateTo('dashboard');
          },
          (loginError) => {
            this.errorMessage =
              'An error occurred during login. Please try again.';
          }
        );
      },
      (error) => {
        this.errorMessage = 'An error occurred. Please try again.';
      }
    );
  }

  cancel(): void {
    this.navigationService.navigateTo('login');
  }
}
