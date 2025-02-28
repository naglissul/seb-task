import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { GraphModalComponent } from '../../components/graph-modal/graph-modal.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ExchangeRate } from '../../../services/gen-client';
import { DataService } from '../../../services/data.service';

@Component({
  selector: 'app-rates-page',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './rates-page.component.html',
  styleUrl: './rates-page.component.css',
})
export class RatesPageComponent {
  constructor(private dialog: MatDialog, private dataService: DataService) {}

  currencyList?: ExchangeRate[];
  selectedCurrency = '';

  ngOnInit() {
    this.dataService.rates$.subscribe((rates) => {
      this.currencyList = rates || [];
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
