import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  //in development, comment one and uncomment the other
  private apiUrl = 'http://127.0.0.1:8080/auth'; //in development
  //private apiUrl = `${window.location.origin}/auth`; // in production

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { username, password });
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
  }
}
