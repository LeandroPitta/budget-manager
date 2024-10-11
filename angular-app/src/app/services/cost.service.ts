import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CostService {

  private apiUrlDevelopement = 'http://127.0.0.1:8080/cost-management-1/cost';
  private apiUrl = `${window.location.origin}/cost`;

  constructor(private http: HttpClient) { }

  getCosts(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getGiftData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/gift`);
  }

  getCost(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addCost(cost: any): Observable<any> {
    return this.http.post(this.apiUrl, cost);
  }

  updateCost(id: string, cost: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, cost);
  }

  deleteCost(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
