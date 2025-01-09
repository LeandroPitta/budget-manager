import { Component, OnInit } from '@angular/core';
import { NavigationService } from '../../core/navigation.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from '../../services/user.service';
import { UserData } from '../../models/user-data';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  titleColors: any[] = [];
  backgroundColors: any[] = [];
  fontFamilies: any[] = [];
  backgroundGifs: any[] = [];
  budgetGifs: any[] = [];

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private navigationService: NavigationService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.loadCustomizationOptions();
  }

  loadCustomizationOptions(): void {
    this.userService.getTitleColors().subscribe((data) => {
      this.titleColors = data;
    });

    this.userService.getBackgroundColor().subscribe((data) => {
      this.backgroundColors = data;
    });

    this.userService.getFontFamilies().subscribe((data) => {
      this.fontFamilies = data;
    });

    this.userService.getBackgroundGifs().subscribe((data) => {
      this.backgroundGifs = data;
    });

    this.userService.getBudgetGifs().subscribe((data) => {
      this.budgetGifs = data;
    });
  }

  register(): void {
    const userData: UserData = {
      username: this.username,
      password: this.password,
      title: '',
      backgroundColorId: 0,
      titleColorId: 0,
      fontFamilyId: 0,
      backgroundGifId: 0,
      budgetGif: ''
    };

    this.userService.register(userData).subscribe(
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
