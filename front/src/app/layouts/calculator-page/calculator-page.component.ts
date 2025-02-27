import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  ExchangeRate,
  MainControllerService,
} from '../../../services/gen-client';

@Component({
  selector: 'app-calculator-page',
  imports: [FormsModule, CommonModule],
  templateUrl: './calculator-page.component.html',
  styleUrl: './calculator-page.component.css',
})
export class CalculatorPageComponent {
  constructor(private apiService: MainControllerService) {}

  currencyList?: ExchangeRate[];
  selectedCurrencyFrom = { currency: '', rate: 0 };
  selectedCurrencyTo = { currency: '', rate: 0 };
  amountFrom = 0;
  calculatedAmountTo = 0;

  ngOnInit() {
    this.apiService.getTodaysRates().subscribe({
      next: (response) => {
        this.currencyList = response;
        this.selectedCurrencyFrom = response[0];
        this.selectedCurrencyTo = response[1];
      },
      error: (error) => console.error('API Error:', error),
    });
  }

  calculate() {
    this.calculatedAmountTo = parseFloat(
      (
        (this.amountFrom / this.selectedCurrencyFrom.rate) *
        this.selectedCurrencyTo.rate
      ).toFixed(8)
    );
  }
}
