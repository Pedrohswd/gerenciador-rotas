import { RotaService } from './../../service/rota.service';
import { Component } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';
import { InputNumberModule } from 'primeng/inputnumber';
import { RadioButtonModule } from 'primeng/radiobutton';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputTextModule } from 'primeng/inputtext';
import { RatingModule } from 'primeng/rating';
import { ToolbarModule } from 'primeng/toolbar';
import { ToastModule } from 'primeng/toast';
import { RippleModule } from 'primeng/ripple';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { TagModule } from 'primeng/tag';
import { FileUploadModule } from 'primeng/fileupload';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { Rotas } from '../../model/rotas';

interface expandedRows {
  [key: string]: boolean;
}


@Component({
  selector: 'app-rotas',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    FileUploadModule,
    TagModule,
    FormsModule,
    ButtonModule,
    RippleModule,
    ToastModule,
    ToolbarModule,
    RatingModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule,
    RadioButtonModule,
    InputNumberModule,
    CalendarModule,
    DialogModule],
  templateUrl: './rotas.component.html',
  styleUrl: './rotas.component.css'
})
export class RotasComponent {
  pedidoDialog: boolean = false;

  deletepedidoDialog: boolean = false;

  deletepedidosDialog: boolean = false;

  isExpanded: boolean = false;

  expandedRows: expandedRows = {};

  selectedDate: Date = new Date()

  filteredRotas: Rotas[] = [];

  rotas: Rotas[] = [];

  rota: Rotas = {
    orders: []
  };

  selectedRota: Rotas[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  constructor(private rotaService: RotaService) { }

  ngOnInit() {
    this.rotaService.getAllRoutes().subscribe(data => {
      this.filteredRotas = data
      this.rotas = data
    });

    this.filterByDate()
  }

  filterByDate() {
    let selectedDate = this.selectedDate;

    if (selectedDate) {
      
      const formattedDate = selectedDate.toISOString().split('T')[0];
      this.filteredRotas = this.rotas.filter(rota => rota.createdAt == formattedDate);

    } else {
      // Se nenhuma data selecionada, mostra todas as rotas
      this.filteredRotas = [...this.rotas];
    }
  }

  expandAll() {
    if (!this.isExpanded) {
      this.rotas.forEach(product => product && product.id ? this.expandedRows[product.id] = true : '');

    } else {
      this.expandedRows = {};
    }
    this.isExpanded = !this.isExpanded;
  }

  deleteSelectedPedidos() {
    this.deletepedidosDialog = true;
  }

  concluirRota(rota: Rotas) {
    this.rotaService.concluirRota(rota).subscribe(resposta => {

    })

  }

  deletePedido(pedido: Rotas) {
    this.deletepedidoDialog = true;
    this.rota = { ...pedido };
  }

  confirmDeleteSelected() {
    this.deletepedidosDialog = false;
    this.rotas = this.rotas.filter(val => !this.selectedRota.includes(val));
    this.selectedRota = [];
  }

  confirmDelete() {
    this.deletepedidoDialog = false;
    this.rotas = this.rotas.filter(val => val.id !== this.rota.id);
  }

  hideDialog() {
    this.pedidoDialog = false;
    this.submitted = false;
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.rotas.length; i++) {
      if (this.rotas[i].id === id) {
        index = i;
        break;
      }
    }

    return index;
  }

  createId(): string {
    let id = '';
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
  }

  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }
}
