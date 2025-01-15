import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NavigationService } from '../../core/navigation.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../../services/user.service';
import { UserDataUpdate } from '../../models/user-data-update';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css'],
  providers: [LoginComponent],
})
export class UpdateComponent implements OnInit {
  @ViewChild('backgroundColorSwiper') backgroundColorSwiper!: ElementRef;
  @ViewChild('titleColorSwiper') titleColorSwiper!: ElementRef;
  @ViewChild('fontFamilySwiper') fontFamilySwiper!: ElementRef;
  @ViewChild('backgroundGifSwiper') backgroundGifSwiper!: ElementRef;
  @ViewChild('budgetGifSwiper') budgetGifSwiper?: ElementRef;

  username: string = '';
  password: string = '';
  title: string = '';
  budgetValue: number = 0;
  backgroundColorId: number = 0;
  titleColorId: number = 0;
  fontFamilyId: number = 0;
  backgroundGifId: number = 0;
  budgetGifId: string = '';
  errorMessage: string = '';
  budgetGifOption: string = 'gif';
  budgetGifText: string = '';
  titleColors: any[] = [];
  backgroundColors: any[] = [];
  fontFamilies: any[] = [];
  backgroundGifs: any[] = [];
  budgetGifs: any[] = [];

  constructor(
    private userService: UserService,
    private navigationService: NavigationService,
    private snackBar: MatSnackBar,
    private loginComponent: LoginComponent
  ) {}

  ngOnInit(): void {
    this.loadCustomizationOptions();
    this.loadFormDataFromLocalStorage();
     this.setBudgetGifOption();
  }

  loadCustomizationOptions(): void {
    this.userService.getTitleColors().subscribe((data) => {
      this.titleColors = this.setFirstOptionFromLocalStorage(
        data.titleColors,
        localStorage.getItem('titleColor') || 'titleColor'
      );
    });

    this.userService.getBackgroundColor().subscribe((data) => {
      this.backgroundColors = this.setFirstOptionFromLocalStorage(
        data.backgroundColors,
        localStorage.getItem('backgroundColor') || 'backgroundColor'
      );
    });

    this.userService.getFontFamilies().subscribe((data) => {
      this.fontFamilies = this.setFirstOptionFromLocalStorage(
        data.fontFamilies,
        localStorage.getItem('fontFamily') || 'fontFamily'
      );
    });

    this.userService.getBackgroundGifs().subscribe((data) => {
      this.backgroundGifs = this.setFirstOptionFromLocalStorage(
        data.backgroundGifs,
        localStorage.getItem('backgroundGif') || 'backgroundGif'
      );
    });

    this.userService.getBudgetGifs().subscribe((data) => {
      this.budgetGifs = this.setFirstOptionFromLocalStorage(
        data.budgetGifs,
        localStorage.getItem('budgetGif') || 'budgetGif'
      );
    });
  }

  private setFirstOptionFromLocalStorage(
    options: any[],
    localStorage: string
  ): any[] {
    if (localStorage) {
      const index = options.findIndex(
        (option) =>
          option.description === localStorage || option.url === localStorage
      );
      if (index > -1) {
        const [selectedOptionObj] = options.splice(index, 1);
        options.unshift(selectedOptionObj);
      }
    }
    return options;
  }

  loadFormDataFromLocalStorage(): void {
    this.username = localStorage.getItem('username') || '';
    this.title = localStorage.getItem('title') || '';
    this.budgetValue = parseFloat(localStorage.getItem('budgetValue') || '0');
  }

  extractFontName(fontDescription: string): string {
    return fontDescription.split(',')[0].replace(/['"]/g, '');
  }

  private setBudgetGifOption(): void {
    const budgetGif = localStorage.getItem('budgetGif');
    if (budgetGif) {
      if (budgetGif.startsWith('http')) {
        this.budgetGifOption = 'gif';
      } else {
        this.budgetGifOption = 'text';
        this.budgetGifText = budgetGif;
      }
    }
  }

  update(): void {
    const backgroundColorSwiper =
      this.backgroundColorSwiper.nativeElement.swiper;
    const titleColorSwiper = this.titleColorSwiper.nativeElement.swiper;
    const fontFamilySwiper = this.fontFamilySwiper.nativeElement.swiper;
    const backgroundGifSwiper = this.backgroundGifSwiper.nativeElement.swiper;
    const budgetGifSwiper = this.budgetGifSwiper?.nativeElement?.swiper;

    this.backgroundColorId =
      this.backgroundColors[backgroundColorSwiper.activeIndex].id;
    this.titleColorId = this.titleColors[titleColorSwiper.activeIndex].id;
    this.fontFamilyId = this.fontFamilies[fontFamilySwiper.activeIndex].id;
    this.backgroundGifId =
      this.backgroundGifs[backgroundGifSwiper.activeIndex].id;
    if (this.budgetGifOption === 'text') {
      this.budgetGifId = this.budgetGifText;
    } else {
      this.budgetGifId =
        this.budgetGifs[budgetGifSwiper.activeIndex].id.toString();
    }

    const userDataUpdate: UserDataUpdate = {
      password: this.password,
      title: this.title,
      budgetValue: this.budgetValue,
      backgroundColorId: this.backgroundColorId,
      titleColorId: this.titleColorId,
      fontFamilyId: this.fontFamilyId,
      backgroundGifId: this.backgroundGifId,
      budgetGif: this.budgetGifId,
    };

    this.userService.update(userDataUpdate).subscribe(
      (response) => {
        this.loginComponent.username = this.username;
        this.loginComponent.password = this.password;
        this.loginComponent.login();
        this.snackBar.open('User successfully updated', 'Close', {
          duration: 3000,
          verticalPosition: 'top',
        });
      },
      (error) => {
        this.errorMessage = 'An error occurred. Please try again.';
      }
    );
  }

  cancel(): void {
    this.navigationService.navigateTo('dashboard');
  }
}
