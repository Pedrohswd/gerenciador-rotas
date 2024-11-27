import { Component } from '@angular/core';
import { Pedido } from '../../model/pedido';
import { Table, TableModule } from 'primeng/table';
import { PedidoService } from '../../service/pedido.service';
import { DialogModule } from 'primeng/dialog';
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
import { FileUploadModule } from 'primeng/fileupload';
import { CommonModule } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { CalendarModule } from 'primeng/calendar';

@Component({
  selector: 'app-pedidos',
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
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.css'
})
export class PedidosComponent {
  pedidoDialog: boolean = false;

  deletepedidoDialog: boolean = false;

  deletepedidosDialog: boolean = false;

  selectedDate: Date = new Date()
  
  filteredPedidos: Pedido[] = [];

  pedidos: Pedido[] = [];

  pedido: Pedido = {};

  selectedPedidos: Pedido[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(private pedidoService: PedidoService) { }

  ngOnInit() {
    this.pedidoService.getAllPedidos().subscribe(data =>{
        this.filteredPedidos = data
        this.pedidos = data
    });
      this.filterByDate()
  }

  filterByDate() {
        let selectedDate = this.selectedDate;
        
        if (selectedDate) {
            // Converte as datas para o início do dia para comparação
            const startOfDay = new Date(selectedDate);
            startOfDay.setHours(0, 0, 0, 0);

            this.filteredPedidos = this.pedidos.filter(pedido => {
                if (pedido.createdAt) {
                    const pedidoDate = new Date(pedido.createdAt);
                    pedidoDate.setHours(0, 0, 0, 0);
                    return pedidoDate.getTime() === startOfDay.getTime();
                }
                return false;
            });
        } else {
            // Se nenhuma data selecionada, mostra todos os pedidos
            this.filteredPedidos = [...this.pedidos];
        }
    }

  openNew() {
      this.pedido = {};
      this.submitted = false;
      this.pedidoDialog = true;
  }

  deleteSelectedPedidos() {
      this.deletepedidosDialog = true;
  }

  gerarRotas(pedidos: Pedido[]){
    this.pedidoService.gerarRotas(pedidos).subscribe(data => {
        console.log(data)
    })
  }

  editPedido(pedido: Pedido) {
      this.pedido = { ...pedido };
      this.pedidoDialog = true;
  }

  deletePedido(pedido: Pedido) {
      this.deletepedidoDialog = true;
      this.pedido = { ...pedido };
  }

  confirmDeleteSelected() {
      this.deletepedidosDialog = false;
      this.pedidos = this.pedidos.filter(val => !this.selectedPedidos.includes(val));
      this.selectedPedidos = [];
  }

  confirmDelete() {
      this.deletepedidoDialog = false;
      this.pedidos = this.pedidos.filter(val => val.id !== this.pedido.id);
      this.pedido = {};
  }

  hideDialog() {
      this.pedidoDialog = false;
      this.submitted = false;
  }

  savePedido() {
      this.submitted = true;

    if (this.pedido.name?.trim()) {
        if (this.pedido.id) {
            // Atualizar pedido existente
            this.pedidoService.updatePedido(this.pedido).subscribe({
                next: (pedidoAtualizado) => {
                    // Atualiza a lista local
                    if(this.pedido.id)
                        this.pedidos[this.findIndexById(this.pedido.id)] = pedidoAtualizado;
                    this.pedidos = [...this.pedidos];
                    this.pedidoDialog = false;
                    this.pedido = {};
                },
                error: (erro) => {
                    console.error('Erro ao atualizar pedido:', erro);
                    // Aqui você pode adicionar uma mensagem de erro para o usuário
                }
            });
        } else {
            // Criar novo pedido
            this.pedidoService.createPedido(this.pedido).subscribe({
                next: (novoPedido) => {
                    this.pedidos.push(novoPedido);
                    this.pedidos = [...this.pedidos];
                    this.pedidoDialog = false;
                    this.pedido = {};
                },
                error: (erro) => {
                    console.error('Erro ao criar pedido:', erro);
                    // Aqui você pode adicionar uma mensagem de erro para o usuário
                }
            });
        }
    }
  }

  findIndexById(id: string): number {
      let index = -1;
      for (let i = 0; i < this.pedidos.length; i++) {
          if (this.pedidos[i].id === id) {
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
