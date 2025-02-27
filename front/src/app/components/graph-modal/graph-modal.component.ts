import { Component, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-graph-modal',
  imports: [],
  templateUrl: './graph-modal.component.html',
  styleUrl: './graph-modal.component.css',
})
export class GraphModalComponent {
  constructor(private dialogRef: MatDialogRef<GraphModalComponent>) {}

  @Input({ required: true }) currencyId!: string;

  ngOnInit() {
    //TODO: fetch history of this currency
    console.log(
      'GraphModalComponent initialized with currencyId:',
      this.currencyId
    );
  }

  close() {
    this.dialogRef.close();
  }
}
