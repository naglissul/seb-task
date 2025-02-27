import { Routes } from '@angular/router';
import { CalculatorPageComponent } from './layouts/calculator-page/calculator-page.component';
import { RatesPageComponent } from './layouts/rates-page/rates-page.component';

export const routes: Routes = [
  { path: '', component: RatesPageComponent },
  { path: 'calculator', component: CalculatorPageComponent },
];
