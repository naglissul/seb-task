import { CommonModule } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import { GraphModalComponent } from '../../components/graph-modal/graph-modal.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import {
  ExchangeRate,
  MainControllerService,
} from '../../../services/gen-client';

@Component({
  selector: 'app-rates-page',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './rates-page.component.html',
  styleUrl: './rates-page.component.css',
})
export class RatesPageComponent {
  constructor(
    private dialog: MatDialog,
    private apiService: MainControllerService
  ) {}

  currencyList?: ExchangeRate[];
  selectedCurrency = '';

  ngOnInit() {
    this.apiService.getTodaysRates().subscribe({
      next: (response) => {
        this.currencyList = response;
      },
      error: (error) => console.error('API Error:', error),
    });
  }

  openDialog(selectedCurrency: string) {
    this.selectedCurrency = selectedCurrency;
    this.dialog.open(GraphModalComponent, {
      panelClass: 'graph-modal',
      data: { currency: selectedCurrency },
    });
  }
}
