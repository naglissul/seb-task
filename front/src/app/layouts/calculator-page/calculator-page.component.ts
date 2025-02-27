import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-calculator-page',
  imports: [FormsModule, CommonModule],
  templateUrl: './calculator-page.component.html',
  styleUrl: './calculator-page.component.css',
})
export class CalculatorPageComponent {
  currencyList = [
    {
      currency: 'USD',
      rate: 1.0,
    },
    {
      currency: 'EUR',
      rate: 0.9,
    },
    {
      currency: 'GBP',
      rate: 0,
    },
  ];

  selectedCurrencyFrom = this.currencyList[0];
  selectedCurrencyTo = this.currencyList[1];
  amountFrom = 0;
  calculatedAmountTo = 0;

  calculate() {
    this.calculatedAmountTo = parseFloat(
      (
        (this.amountFrom / this.selectedCurrencyFrom.rate) *
        this.selectedCurrencyTo.rate
      ).toFixed(8)
    );
  }
}
