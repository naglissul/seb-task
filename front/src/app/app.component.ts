import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MainControllerService, MessageDto } from '../services/gen-client';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'exrates-client';
  data?: MessageDto;

  constructor(private apiService: MainControllerService) {}

  ngOnInit() {
    this.apiService.hello().subscribe({
      next: (response) => {
        this.data = response;
      },
      error: (error) => console.error('API Error:', error),
    });
  }
}
