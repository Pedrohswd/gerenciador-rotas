import { Injectable } from '@angular/core';
import { MenuChangeEvent } from '../../template/sakai-ng/src/app/layout/api/menuchangeevent';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  private menuSource = new Subject<MenuChangeEvent>();
  private resetSource = new Subject();

  menuSource$ = this.menuSource.asObservable();
  resetSource$ = this.resetSource.asObservable();

  onMenuStateChange(event: MenuChangeEvent) {
      this.menuSource.next(event);
  }

  reset() {
      this.resetSource.next(true);
  }
}
