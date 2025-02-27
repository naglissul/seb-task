import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  CurrencyHistoryDay,
  MainControllerService,
} from '../../../services/gen-client';
import { CommonModule } from '@angular/common';
import { NGX_ECHARTS_CONFIG, NgxEchartsModule } from 'ngx-echarts';
import * as echarts from 'echarts/core';
import { LineChart } from 'echarts/charts';
import {
  GridComponent,
  TooltipComponent,
  TitleComponent,
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([
  LineChart,
  GridComponent,
  TooltipComponent,
  TitleComponent,
  CanvasRenderer,
]);

@Component({
  selector: 'app-graph-modal',
  imports: [CommonModule, NgxEchartsModule],
  templateUrl: './graph-modal.component.html',
  styleUrl: './graph-modal.component.css',
  providers: [
    {
      provide: NGX_ECHARTS_CONFIG,
      useValue: { echarts },
    },
  ],
})
export class GraphModalComponent {
  currencyHistory?: CurrencyHistoryDay[];

  constructor(
    private dialogRef: MatDialogRef<GraphModalComponent>,
    private apiService: MainControllerService,
    @Inject(MAT_DIALOG_DATA) public data: { currency: string }
  ) {}

  chartOptions: any = {
    xAxis: { type: 'category', data: [] },
    yAxis: { type: 'value' },
    series: [{ data: [], type: 'line' }],
    tooltip: { trigger: 'axis' },
  };

  ngOnInit() {
    this.apiService.getOneCurrencyHistory(this.data.currency).subscribe({
      next: (response) => {
        this.currencyHistory = response;

        const rates = response.map((day) => day.rate);
        const minRate = Math.min(...rates);
        const maxRate = Math.max(...rates);

        this.chartOptions = {
          xAxis: {
            type: 'category',
            data: response.map((day) => day.date).reverse(),
          },
          yAxis: {
            type: 'value',
            min: minRate,
            max: maxRate,
          },
          series: [
            {
              data: rates.reverse(),
              type: 'line',
            },
          ],
          tooltip: {
            trigger: 'axis',
          },
        };
      },
      error: (error) => console.error('API Error:', error),
    });
  }

  close() {
    this.dialogRef.close();
  }
}
