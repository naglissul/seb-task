import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { RatesPageComponent } from './rates-page.component';
import { GraphModalModule } from '../../components/graph-modal/graph-modal.module';

@NgModule({
  declarations: [RatesPageComponent],
  imports: [CommonModule, MatDialogModule, GraphModalModule],
})
export class RatesPageModule {}
