import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { GraphModalComponent } from '../../components/graph-modal/graph-modal.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-rates-page',
  imports: [CommonModule, GraphModalComponent],
  templateUrl: './rates-page.component.html',
  styleUrl: './rates-page.component.css',
})
export class RatesPageComponent {
  constructor(private dialog: MatDialog) {}

  selectedCurrencyId = '';
  rates = [
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

  ratesHistory = [
    {
      date: '2021-01-01',
      rates: [
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
      ],
    },
    {
      date: '2021-01-02',
      rates: [
        {
          currency: 'USD',
          rate: 1.1,
        },
        {
          currency: 'EUR',
          rate: 0.8,
        },
        {
          currency: 'GBP',
          rate: 0.1,
        },
      ],
    },
  ];

  openDialog(selectedCurrencyId: string) {
    this.selectedCurrencyId = selectedCurrencyId;
    this.dialog.open(GraphModalComponent, { width: '400px' });
  }
}
