import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserData } from '../models/user-data';
import { UserDataUpdate } from '../models/user-data-update';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  //in development, comment one and uncomment the other
  private apiUrl = 'http://127.0.0.1:8080/user'; //in development
  //private apiUrl = `${window.location.origin}/user`; // in production

  constructor(private http: HttpClient) {}

  register(userData: UserData): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  update(userDataUpdate: UserDataUpdate): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put(`${this.apiUrl}/update`, userDataUpdate, { headers });
  }

  getTitleColors(): Observable<any> {
    return this.http.get(`${this.apiUrl}/title-colors`);
  }

  getBackgroundColor(): Observable<any> {
    return this.http.get(`${this.apiUrl}/background-colors`);
  }

  getFontFamilies(): Observable<any> {
    return this.http.get(`${this.apiUrl}/font-families`);
  }

  getBackgroundGifs(): Observable<any> {
    return this.http.get(`${this.apiUrl}/background-gifs`);
  }

  getBudgetGifs(): Observable<any> {
    return this.http.get(`${this.apiUrl}/budget-gifs`);
  }
}
