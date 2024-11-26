import { Pedido } from "./pedido";

export interface Rotas {
    id?: any;
    orders: Pedido[];
    distanciaTotal?: number
    duracaoTotal?: number
    createdAt?: string;
}