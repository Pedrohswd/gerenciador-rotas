import { Component, ElementRef } from '@angular/core';
import { MenuComponent } from '../menu/menu.component';
import { LayoutService } from '../../service/layout.service';

@Component({
  selector: 'app-slidebar',
  standalone: true,
  imports: [
    MenuComponent
  ],
  templateUrl: './slidebar.component.html',
  styleUrl: './slidebar.component.css'
})
export class SlidebarComponent {
  
  constructor(public layoutService: LayoutService, public el: ElementRef) { }

}
