import { Component } from '@angular/core';
import { NavigationService } from './core/navigation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-app';

  constructor(public navigationService: NavigationService) { }
}