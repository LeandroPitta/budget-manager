import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CostService {

  //in development, comment one and uncomment the other
  //private apiUrl = 'http://127.0.0.1:8080/cost-management-1/cost'; //in development

  private apiUrl = `${window.location.origin}/cost`; //in production

  constructor(private http: HttpClient) { }

  getCosts(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this.apiUrl, { headers });
  }

  getGiftData(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.apiUrl}/gift`, { headers });
  }

  getCost(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addCost(cost: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(this.apiUrl, cost, { headers });
  }
}
