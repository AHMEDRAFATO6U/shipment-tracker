import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { WebsocketService } from '../services/websocket-service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [NgClass],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header implements OnInit {
  private destoryRef = inject(DestroyRef);
  private websocketService = inject(WebsocketService);

  title = 'Shipment Tracker';
  isConnected = signal(false);

  ngOnInit(): void {
    this.websocketService
      .isConnected()
      .pipe(takeUntilDestroyed(this.destoryRef))
      .subscribe((connected) => this.isConnected.set(connected));
  }
}
