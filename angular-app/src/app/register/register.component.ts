import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  register(): void {
    this.authService.register(this.username, this.password).subscribe(() => {
      this.router.navigate(['/login']);
    },
      error => {
        this.errorMessage = 'An error occurred. Please try again.';
      }
    );
  }

  cancel(): void {
    this.router.navigate(['/login']);
  }
}
