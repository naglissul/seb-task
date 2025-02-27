import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { NgxEchartsModule, NGX_ECHARTS_CONFIG } from 'ngx-echarts';
import { GraphModalComponent } from './graph-modal.component';

@NgModule({
  declarations: [GraphModalComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    NgxEchartsModule.forRoot({ echarts: () => import('echarts') }), // ✅ Setup global config
  ],
})
export class GraphModalModule {}
