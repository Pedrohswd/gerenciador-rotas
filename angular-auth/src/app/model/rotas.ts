import { Pedido } from "./pedido";

export interface Rotas {
    id: any;
    pedidos: Pedido[];
    distanciaTotal: number
}