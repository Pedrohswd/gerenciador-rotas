import { API_CONFIG } from './../config/API_CONFIG';
import { Pedido } from './../model/pedido';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rotas } from '../model/rotas';
import { AuthService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  constructor(private http: HttpClient,
    private authSerive: AuthService
  ) {}

  getAllPedidos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${API_CONFIG.baseUrl}/orders`);
  }

  getAllByUser(): Observable<Pedido[]> {
    const username = this.authSerive.getUsername()
    return this.http.get<Pedido[]>(`${API_CONFIG.baseUrl}/orders/${username}`);
  }

  createPedido(pedido: Pedido): Observable<Pedido> {
    return this.http.post<Pedido>(`${API_CONFIG.baseUrl}/orders/create`, pedido);
  }

  updatePedido(pedido: Pedido): Observable<Pedido> {
      return this.http.put<Pedido>(`${API_CONFIG.baseUrl}/${pedido.id}`, pedido);
  }

  gerarRotas(pedidos: Pedido[]): Observable<Rotas[]> {
    return this.http.post<Rotas[]>(`${API_CONFIG.baseUrl}/routes/otimizar`, pedidos);
  }

}
