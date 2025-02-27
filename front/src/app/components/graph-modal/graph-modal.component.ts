import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  CurrencyHistoryDay,
  MainControllerService,
} from '../../../services/gen-client';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-graph-modal',
  imports: [CommonModule],
  templateUrl: './graph-modal.component.html',
  styleUrl: './graph-modal.component.css',
})
export class GraphModalComponent {
  currencyHistory?: CurrencyHistoryDay[];

  constructor(
    private dialogRef: MatDialogRef<GraphModalComponent>,
    private apiService: MainControllerService,
    @Inject(MAT_DIALOG_DATA) public data: { currency: string }
  ) {}

  ngOnInit() {
    this.apiService.getOneCurrencyHistory(this.data.currency).subscribe({
      next: (response) => {
        this.currencyHistory = response;
      },
      error: (error) => console.error('API Error:', error),
    });
  }

  close() {
    this.dialogRef.close();
  }
}
