import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MainControllerService, ExchangeRate } from '../services/gen-client';

@Injectable({
  providedIn: 'root', // global singleton
})
export class DataService {
  private ratesSubject = new BehaviorSubject<ExchangeRate[] | null>(null);
  rates$ = this.ratesSubject.asObservable();

  constructor(private apiService: MainControllerService) {}

  fetchRates() {
    this.apiService.getTodaysRates().subscribe({
      next: (rates) => this.ratesSubject.next(rates),
      error: (err) => console.error('Failed to fetch rates', err),
    });
  }
}
