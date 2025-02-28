import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ExchangeRate } from '../../../services/gen-client';
import { DataService } from '../../../services/data.service';

@Component({
  selector: 'app-calculator-page',
  imports: [FormsModule, CommonModule],
  templateUrl: './calculator-page.component.html',
  styleUrl: './calculator-page.component.css',
})
export class CalculatorPageComponent {
  constructor(private dataService: DataService) {}

  currencyList?: ExchangeRate[];
  selectedCurrencyFrom = { currency: '', rate: 0 };
  selectedCurrencyTo = { currency: '', rate: 0 };
  amountFrom = 0;
  calculatedAmountTo = 0;

  ngOnInit() {
    this.dataService.rates$.subscribe((rates) => {
      this.currencyList = rates || [];
      if (this.currencyList && this.currencyList.length > 0) {
        this.selectedCurrencyFrom = this.currencyList[0];
        this.selectedCurrencyTo = this.currencyList[1];
      }
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

  reverseCurrencies() {
    const temp = this.selectedCurrencyFrom;
    this.selectedCurrencyFrom = this.selectedCurrencyTo;
    this.selectedCurrencyTo = temp;
    const tempAmount = this.amountFrom;
    this.amountFrom = this.calculatedAmountTo;
    this.calculatedAmountTo = tempAmount;
    this.calculate();
  }
}
